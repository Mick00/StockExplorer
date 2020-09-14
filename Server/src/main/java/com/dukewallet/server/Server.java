package com.dukewallet.server;

import com.dukewallet.database.Database;
import com.dukewallet.models.assets.AssetValue;
import com.dukewallet.models.assets.stocks.Stock;
import com.dukewallet.server.dataFetchers.StockDataFetcher;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.execution.ExecutionContextBuilder;
import graphql.schema.GraphQLSchema;
import graphql.schema.PropertyDataFetcher;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.*;
import org.json.JSONObject;
import spark.Spark;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class Server {

    private APIResolver apiResolver;

    public Server(){
        Database.init();
        apiResolver = new APIResolver();
    }

    public TypeDefinitionRegistry getTypeRegistry(){
        String schema = null;
        try {
            schema = this.getSchema();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SchemaParser schemaParser = new SchemaParser();
        return schemaParser.parse(schema);
    }

    public String getSchema() throws FileNotFoundException {
        BufferedReader reader = getSchemaFileReader();
        return reader.lines().collect(Collectors.joining());
    }

    public BufferedReader getSchemaFileReader() throws FileNotFoundException {
        File schemaFile = new File("Server/src/main/resources/graphQL/schema.graphqls");
        if (!schemaFile.exists()){
            System.out.println("Schema file not found");
        }
        return new BufferedReader(new FileReader(schemaFile));
    }

    public RuntimeWiring getRuntimeWiring(){
        return apiResolver.getWiring();
    }

    public static void main(String[] args){
        Server server = new Server();
        Spark.get("/hello", (req, res) -> "Hello World");
        Spark.post("/graphql",(req, res) -> {

            TypeDefinitionRegistry typeDefinitionRegistry = server.getTypeRegistry();

            RuntimeWiring runtimeWiring = server.getRuntimeWiring();
            SchemaGenerator schemaGenerator = new SchemaGenerator();
            GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
            GraphQL gql = GraphQL.newGraphQL(graphQLSchema).build();

            JSONObject json = new JSONObject(req.body());
            ExecutionInput.Builder input = ExecutionInput.newExecutionInput()
                    .query(json.getString("query"));
            if(json.has("variables")){
                input.variables(json.getJSONObject("variables").toMap());
            }
            ExecutionResult executionResult = gql.execute(input.build());
            JSONObject formatted = new JSONObject();
            LinkedHashMap<String, LinkedHashMap> data = executionResult.getData();
            formatted.put("data", data);
            if (executionResult.getErrors().size()>0){
                formatted.put("errors", executionResult.getErrors());
            }

            System.out.println("QUERY:"+json.getString("query"));
            System.out.println("RESULT:"+formatted);
            res.header("Content-Type", "application/json");
            res.header("Access-Control-Allow-Origin","*");
            return formatted;
        });
        Spark.options("/*", (request,response)->{
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if(accessControlRequestMethod != null){
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            response.header("Access-Control-Allow-Origin","*");
            return "OK";
        });
    }
}

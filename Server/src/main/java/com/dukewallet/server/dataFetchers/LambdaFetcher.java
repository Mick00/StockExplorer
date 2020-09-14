package com.dukewallet.server.dataFetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class LambdaFetcher<T, R> implements DataFetcher {

  private final Operation<T, R> op;

    public LambdaFetcher(Operation<T, R> op){
        this.op = op;
    }

    @Override
    public R get(DataFetchingEnvironment environment) throws Exception {
        T t = (T)environment.getSource();
        return op.execute(t);
    }

    public interface Operation<T, R>{
        R execute(T t);
    }

}

package com.dxm.dxmcharge.logic.network.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class AppConverterFactory extends Converter.Factory {


    public static AppConverterFactory create(){
        return create(new Gson());
    }


    @SuppressWarnings("ConstantConditions")
    public static AppConverterFactory create(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        return new AppConverterFactory(gson);
    }

    private final Gson gson;

    private AppConverterFactory(Gson gson) {
        this.gson = gson;
    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new APPResponseBodyConverter<>(adapter);
    }








}

package com.pagination.with.recyclerview.remote.exception;

import android.net.ParseException;
import com.google.gson.JsonParseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.pagination.with.recyclerview.remote.HttpConstants;

public class ExceptionHandler {

    public static String getErrorMessage(Throwable throwable){

        String errorMessage;

        if(throwable instanceof UnknownHostException)
        {
            errorMessage = "We can not find the server at "+ HttpConstants.HOST_URL+" If that address is correct, here are three other things you can try :\n" +
                    "\n" +
                    "1) Try again later.\n" +
                    "2) Check your network connection.\n" +
                    "3) If you are connected, Check that Tutti Artist has internet permission";
        }
        else if(throwable instanceof ConnectException)
        {
            errorMessage = "Host and Port combination not valid, Connection refused";
        }
        else if(throwable instanceof SocketTimeoutException || throwable instanceof ConnectTimeoutException)
        {
            errorMessage = "Connection timeOut error";
        }
        else if(throwable instanceof JsonParseException || throwable instanceof JSONException || throwable instanceof JsonSyntaxException || throwable instanceof JsonSerializer || throwable instanceof NotSerializableException || throwable instanceof ParseException || throwable instanceof MalformedJsonException)
        {
            errorMessage = "Parsing error! Something went wrong api is not responding properly";
        }
        else if(throwable instanceof javax.net.ssl.SSLHandshakeException)
        {
            errorMessage = "javax.net.ssl.SSLHandshakeException, Certificate verification failed";
        }
        else if (throwable instanceof SocketException)
        {
            errorMessage = "File stream is closed, download is suspended";
        }
        else if (throwable instanceof ProtocolException)
        {
            errorMessage = "File stream closed unexpectedly";
        }
        else if (throwable instanceof IOException)
        {
            errorMessage = "File stream error";
        }
        else if(throwable instanceof ClassCastException)
        {
            errorMessage = "Type conversion error";
        }
        else if(throwable instanceof IllegalStateException)
        {
            errorMessage = "Illegal State Exception";
        }
        else if(throwable instanceof retrofit2.HttpException)
        {
            retrofit2.HttpException httpException = (retrofit2.HttpException) throwable;
            ExceptionDetails exceptionDetails = HTTPResponseStatusCodes.getCodeInfo(httpException.code());
            errorMessage = exceptionDetails.getMessage();
        }
        else if(throwable instanceof NullPointerException)
        {
            errorMessage = "Null pointer error";
        }
        else
        {
            errorMessage = throwable.getMessage();

            if (errorMessage == null || errorMessage.length() <= 40)
            {
                errorMessage = "Something went wrong, please try again later.";
            }
        }
        return errorMessage;
    }
}

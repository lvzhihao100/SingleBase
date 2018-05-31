package com.gamerole.common.http;

import com.gamerole.common.base.presenter.BasePresenter;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * Created by lvzhihao on 17-7-4.
 */

public abstract class JsonCallBack<T> extends AbsCallback<T> {


    private BasePresenter mBasePresenter;

    private boolean showDialog = true;

    public JsonCallBack(BasePresenter basePresenter) {
        this.mBasePresenter = basePresenter;
    }


    public JsonCallBack(BasePresenter basePresenter, boolean isShow) {
        this.mBasePresenter = basePresenter;
        showDialog = isShow;
    }

    public JsonCallBack() {
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        if (mBasePresenter != null) {
            if (showDialog) {
                mBasePresenter.showLoading();
            }
        }
    }


    @Override
    public void onFinish() {
        super.onFinish();
        if (mBasePresenter != null) {

            mBasePresenter.hideLoading();
        }
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws JsonParseException {
        ResponseBody body = response.body();
        if (body == null) return null;
        T data = null;
        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(body.charStream());
            Type genericSuperclass = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            data = gson.fromJson(jsonReader, type);
        } catch (JsonIOException e) {
            throw new JsonParseException();
        } catch (JsonSyntaxException e) {
            throw new JsonParseException();
        }
        return data;
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        if (mBasePresenter != null) {
            mBasePresenter.hideLoading();
        }
//        if (response.getException() instanceof JsonParseException) {
//            ToastUtil.showShort("数据解析异常");
//        } else if (response.getException() instanceof UnknownHostException) {
//            ToastUtil.showShort("网络异常");
//        } else if (response.getException() instanceof TimeoutException) {
//            ToastUtil.showShort("网络连接超时");
//        } else if (response.getException() instanceof ConnectException||response.getException() instanceof SocketTimeoutException) {
//            ToastUtil.showShort("网络异常");
//        }
    }
}

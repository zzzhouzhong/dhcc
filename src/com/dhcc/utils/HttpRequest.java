package com.dhcc.utils;

public class HttpRequest implements Runnable {
	private HttpRequestListener mListener;

	public HttpRequest() {
		// TODO Auto-generated constructor stub
	}

	public HttpRequest(HttpRequestListener listener) {
		// TODO Auto-generated constructor stub
		this.mListener = listener;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	interface HttpRequestListener {
		public void onResult();

		public void onError();
	}

}

package com.hubertyoung.common.net.exception;

/**
 * 作者：JIUU on 2017-7-10 16:00:51
 * QQ号：1344393464
 * 作用
 */

public class ServerException extends RuntimeException {
	public int status;
	public String result;

	public ServerException( String result, int status ) {
		this.result = result;
		this.status = status;
	}

	@Override
	public String toString() {
		return result;
	}
}

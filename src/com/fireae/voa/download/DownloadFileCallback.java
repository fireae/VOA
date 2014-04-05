package com.fireae.voa.download;

public interface DownloadFileCallback {

	void downloadSuccess(Object obj);//下载成功
	void downloadError(Exception e,String msg);//下载失败

}

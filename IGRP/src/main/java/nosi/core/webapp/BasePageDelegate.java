package nosi.core.webapp;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import nosi.core.webapp.bpmn.InterfaceBPMNTask;
import nosi.core.webapp.bpmn.RuntimeTask;
import nosi.core.webapp.webservices.helpers.FileRest;

public abstract class BasePageDelegate {

	public QueryString<String, Object> addQueryString(String name, Object value, Predicate<Object> predicate) {
		return predicate.test(value) ? addQueryString(name, value) : queryString();
	}
	
	public QueryString<String, Object> addQueryString(String name, Object value) {
		return Igrp.getInstance().getCurrentController().addQueryString(name, value);
	}
	
	public Response forward(String app, String page, String action, QueryString<String, Object> queryString) {
		return Igrp.getInstance().getCurrentController().forward(app, page, action, queryString);
	}
	
	public Response forward(String app, String page, String action) {
		return Igrp.getInstance().getCurrentController().forward(app, page, action);
	}
	
	public QueryString<String, Object> queryString() {
        return Igrp.getInstance().getCurrentController().queryString();
    }
	
	public Response redirect(String app, String page, String action) throws IOException {
		return Igrp.getInstance().getCurrentController().redirect(app, page, action);
	}
	
	public Response redirect(String r) {
		return Igrp.getInstance().getCurrentController().redirect(r);
	}
	
	public Response redirect(String r, String qs) throws IOException{
		return Igrp.getInstance().getCurrentController().redirect(r, qs);
	}
	
	public Response redirect(String app, String page, String action, Model model) throws IOException{
		return Igrp.getInstance().getCurrentController().redirect(app, page, action, model);
	}
	
	public Response redirect(String app, String page, String action, QueryString<String, Object> queryString) throws IOException{
		return Igrp.getInstance().getCurrentController().redirect(app, page, action, queryString);
	}
	
	public Response redirect(String app, String page, String action, String qs) throws IOException{
		return Igrp.getInstance().getCurrentController().redirect(app, page, action, qs);
	}
	
	public Response redirect(String app, String page, String action, Model model, QueryString<String, Object> queryString) throws IOException{
		return Igrp.getInstance().getCurrentController().redirect(app, page, action, model, queryString);
	}
	
	public Response redirect(String app, String page, String action, QueryString<String, Object> queryString, int isPublic) throws IOException{
		return Igrp.getInstance().getCurrentController().redirect(app, page, action, queryString, isPublic);
	}
	
	public Response redirect(String app, String page, String action,  String[] paramNames, String[] paramValues) throws IOException{
		return Igrp.getInstance().getCurrentController().redirect(app, page, action, paramNames, paramValues);
	}
	
	public Response redirectError() throws IOException{
		return Igrp.getInstance().getCurrentController().redirectError();
	}
	
	public Response redirectToUrl(String url){
		return Igrp.getInstance().getCurrentController().redirectToUrl(url);
	}
	
	public Response sendFile(File file, String name, String contentType, boolean download){
		return Igrp.getInstance().getCurrentController().sendFile(file, name, contentType, download);
	}
	
	public Response xSend(byte[] file, String name, String contentType, boolean download){
		return Igrp.getInstance().getCurrentController().xSend(file, name, contentType, download);
	}
	
	public Response xSend(byte[] file, String name, String contentType, boolean download, String url){
		return Igrp.getInstance().getCurrentController().xSend(file, name, contentType, download, url);
	}
	
	public Response xSend(FileRest file, String name, String contentType, boolean download){
		return Igrp.getInstance().getCurrentController().xSend(file, name, contentType, download);
	}
	
	public Response renderView(View view) throws IOException {
		return Igrp.getInstance().getCurrentController().renderView(view);
	}
	
	public Response renderView(View view, boolean isRenderPartial) throws IOException {
		return Igrp.getInstance().getCurrentController().renderView(view, isRenderPartial);
	}
	
	public Response renderView(String content) throws IOException {
		return Igrp.getInstance().getCurrentController().renderView(content);
	}
	
	public Response renderView(String app, String page, View v, InterfaceBPMNTask bpmn, RuntimeTask runtimeTask) throws IOException {
		return Igrp.getInstance().getCurrentController().renderView(app, page, v, bpmn, runtimeTask);
	}
	
	public Response renderView(String app, String page, View v) throws IOException {
		return Igrp.getInstance().getCurrentController().renderView(app, page, v);
	}
	
}

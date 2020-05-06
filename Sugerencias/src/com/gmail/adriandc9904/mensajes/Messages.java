package com.gmail.adriandc9904.mensajes;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.adriandc9904.Sugerencias;

public class Messages {
	
	@SuppressWarnings("unused")
	private Sugerencias plugin;
	
	public Messages(Sugerencias plugin) {
		this.plugin = plugin;
		FileConfiguration messages = plugin.getMessages();
		
		Mensajes(messages);
	}
	
	private String joinEvent;
	private String unwrittenSug;
	private String sugFormatHelp;
	private String emptyList;
	private String negativeIndex;
	private String topIndex;
	private String invalidIndex;
	private String readFormat;
	private String deleteFormat;
	private String deleteSuccess;
	private String listHeader;
	private String listPageHeader;
	private String emptyPage;
	private String invalidPage;
	private String listImportantHeader;
	private String listFormat;
	private String successReload;
	private String setImportantSuccessfully;
	private String importantFormat;
	private String sugAmount;
	private String success;
	private String cooldown;
	private List<String> helpList;
	
	public void Mensajes(FileConfiguration messages) {
		joinEvent = messages.getString("join-event");
		unwrittenSug = messages.getString("unwritten-suggestion");
		sugFormatHelp = messages.getString("sug-format-help");
		emptyList = messages.getString("empty-list");
		negativeIndex = messages.getString("negative-index");
		topIndex = messages.getString("top-index");
		invalidIndex = messages.getString("invalid-index");
		readFormat = messages.getString("read-format");
		deleteFormat = messages.getString("delete-format");
		deleteSuccess = messages.getString("delete-success");
		listHeader = messages.getString("list-header");
		listPageHeader = messages.getString("list-page-header");
		emptyPage = messages.getString("empty-page");
		invalidPage = messages.getString("invalid-page");
		listImportantHeader = messages.getString("list-important-header");
		listFormat = messages.getString("list-format");
		successReload = messages.getString("success-reload");
		setImportantSuccessfully = messages.getString("set-important-successfully");
		importantFormat = messages.getString("important-format");
		sugAmount = messages.getString("sug-amount");
		success = messages.getString("success");
		cooldown = messages.getString("cooldown");
		helpList = messages.getStringList("help-list");
	}
	
	public String joinEvent() {
		return joinEvent;
	}
	
	public String unwrittenSug() {
		return unwrittenSug;
	}
	
	public String sugFormatHelp() {
		return sugFormatHelp;
	}
	
	public String emptyList() {
		return emptyList;
	}
	
	public String negativeIndex() {
		return negativeIndex;
	}
	
	public String topIndex() {
		return topIndex;
	}
	
	public String invalidIndex() {
		return invalidIndex;
	}
	
	public String readFormat() {
		return readFormat;
	}
	
	public String deleteFormat() {
		return deleteFormat;
	}
	
	public String deleteSuccess() {
		return deleteSuccess;
	}
	
	public String listHeader() {
		return listHeader;
	}
	
	public String listPageHeader() {
		return listPageHeader;
	}
	
	public String emptyPage() {
		return emptyPage;
	}
	
	public String invalidPage() {
		return invalidPage;
	}
	
	public String listImportantHeader() {
		return listImportantHeader;
	}
	
	public String listFormat() {
		return listFormat;
	}
	
	public String successReload() {
		return successReload;
	}
	
	public String setImportantSuccessfully() {
		return setImportantSuccessfully;
	}
	
	public String importantFormat() {
		return importantFormat;
	}
	
	public String sugAmount() {
		return sugAmount;
	}
	
	public String success() {
		return success;
	}
	
	public String cooldown() {
		return cooldown;
	}
	
	public List<String> helpList() {
		return helpList;
	}
}
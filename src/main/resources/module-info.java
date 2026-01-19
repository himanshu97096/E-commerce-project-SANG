module SANGProject {
	exports com.sangspringproject.SANGSpringProject.dao;
	exports com.sangspringproject.SANGSpringProject;
	exports com.sangspringproject.SANGSpringProject.configuration;
	exports com.sangspringproject.SANGSpringProject.controller;
	exports com.sangspringproject.SANGSpringProject.models;
	exports com.sangspringproject.SANGSpringProject.services;

	requires java.desktop;
	requires java.naming;
	requires java.persistence;
	requires java.sql;
	requires net.bytebuddy;
	requires org.apache.tomcat.embed.core;
	requires org.hibernate.orm.core;
	requires spring.beans;
	requires spring.boot;
	requires spring.boot.autoconfigure;
	requires spring.context;
	requires spring.core;
	requires spring.jdbc;
	requires spring.orm;
	requires spring.security.config;
	requires spring.security.core;
	requires spring.security.crypto;
	requires spring.security.web;
	requires spring.tx;
	requires spring.web;
	requires spring.webmvc;
}
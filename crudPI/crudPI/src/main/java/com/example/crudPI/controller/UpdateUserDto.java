package com.example.crudPI.controller;

public record UpdateUserDto(String name, String email, String password, String cep, String phone) {
}

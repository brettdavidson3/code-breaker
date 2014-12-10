package com.lateralus.codebreaker.controller;

import com.lateralus.codebreaker.model.World;

public interface CodeController {

    public void initialize(World world);
    public void update(World world, float delta);

}

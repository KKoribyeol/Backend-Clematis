package com.dsm.kkoribyeol.controller

import org.springframework.web.bind.annotation.*

@RestController
class UserController {

    @PostMapping("/user/a")
    fun test1() = 1

    @GetMapping("/user/a")
    fun test2() = 2

    @PatchMapping("/user/a")
    fun test3() = 3

    @PutMapping("/user/a")
    fun test4() = 4

    @DeleteMapping("/user/a")
    fun test5() = 5

    @PostMapping("/test")
    fun test6() = 6
}
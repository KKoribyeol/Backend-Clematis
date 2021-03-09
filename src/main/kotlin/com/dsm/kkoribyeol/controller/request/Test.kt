package com.dsm.kkoribyeol.controller.request

import javax.validation.constraints.Min

data class Test(@get:Min(5) val a: Int)
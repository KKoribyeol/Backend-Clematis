package com.dsm.clematis.global.filter

import org.slf4j.LoggerFactory
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class LogFilter : Filter {
    private val logger = LoggerFactory.getLogger(LogFilter::class.java)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpServletRequest = request as HttpServletRequest
        logger.info("[${httpServletRequest.method}] ${httpServletRequest.requestURI}")
        chain?.doFilter(request, response)
    }
}
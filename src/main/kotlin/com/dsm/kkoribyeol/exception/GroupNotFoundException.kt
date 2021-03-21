package com.dsm.kkoribyeol.exception

import com.dsm.kkoribyeol.exception.handler.CommonException
import org.springframework.http.HttpStatus

class GroupNotFoundException(
    projectCode: String,
    groupName: String,
) : CommonException(
    code = "GROUP_NOT_FOUND",
    message = "그룹을 찾을 수 없습니다. [projectCode: $projectCode, groupName: $groupName]",
    status = HttpStatus.NOT_FOUND,
)
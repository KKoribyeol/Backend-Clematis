package com.dsm.clematis.domain.group.exception

import com.dsm.clematis.global.exception.CommonException
import org.springframework.http.HttpStatus

class AlreadyExistGroupException(
    projectCode: String,
    groupName: String,
) : CommonException(
    code = "ALREADY_EXIST_GROUP",
    message = "이미 존재하는 그룹입니다. [projectCode = $projectCode, groupName = $groupName]",
    status = HttpStatus.BAD_REQUEST,
)
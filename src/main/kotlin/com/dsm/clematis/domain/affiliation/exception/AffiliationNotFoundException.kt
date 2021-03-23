package com.dsm.clematis.domain.affiliation.exception

import com.dsm.clematis.global.exception.CommonException
import org.springframework.http.HttpStatus

class AffiliationNotFoundException(
    groupName: String,
    targetToken: String,
) : CommonException(
    code = "AFFILIATION_NOT_FOUND",
    message = "소속 정보가 존재하지 않습니다. [GroupName: $groupName, TargetToken: $targetToken]",
    status = HttpStatus.NOT_FOUND,
)
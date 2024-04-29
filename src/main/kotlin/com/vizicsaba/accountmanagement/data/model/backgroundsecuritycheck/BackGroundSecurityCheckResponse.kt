package com.vizicsaba.accountmanagement.data.model.backgroundsecuritycheck

import com.fasterxml.jackson.annotation.JsonCreator

data class BackGroundSecurityCheckResponse(val accountNumber: String = "", val isSecurityCheckSuccess: Boolean = false) {
    @JsonCreator constructor() : this("", false)
}

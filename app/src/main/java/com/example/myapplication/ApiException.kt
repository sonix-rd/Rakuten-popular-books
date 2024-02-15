package com.example.myapplication

import com.example.myapplication.model.response.BaseResponse
import java.lang.RuntimeException

class ApiException(errorResponse: BaseResponse): RuntimeException(errorResponse.errorMessage, null)
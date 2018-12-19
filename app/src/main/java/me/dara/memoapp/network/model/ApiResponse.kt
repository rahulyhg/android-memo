package me.dara.memoapp.network.model

/**
 * @author sardor
 */
class ApiResponse<T>(val obj: T?,val status: Status)

enum class Status {
  SUCCESS,
  FAIL
}
package com.gil.parkspace

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ParkingLot(
    @JsonProperty("PARKING_CODE")
    val parkingCode: String,

    @JsonProperty("PARKING_NAME")
    val parkingName: String,

    @JsonProperty("ADDR")
    val address: String,

    @JsonProperty("PARKING_TYPE")
    val parkingType: String,

    @JsonProperty("PARKING_TYPE_NM")
    val parkingTypeName: String,

    @JsonProperty("OPERATION_RULE")
    val operationRule: String,

    @JsonProperty("OPERATION_RULE_NM")
    val operationRuleName: String,

    @JsonProperty("TEL")
    val telephone: String,

    @JsonProperty("QUE_STATUS")
    val queStatus: String,

    @JsonProperty("QUE_STATUS_NM")
    val queStatusName: String,

    @JsonProperty("CAPACITY")
    var capacity: Double,

    @JsonProperty("CUR_PARKING")
    val currentParking: Double,

    @JsonProperty("CUR_PARKING_TIME")
    val currentParkingTime: String,

    @JsonProperty("PAY_YN")
    val payYN: String,

    @JsonProperty("PAY_NM")
    val payName: String,

    @JsonProperty("NIGHT_FREE_OPEN")
    val nightFreeOpen: String,

    @JsonProperty("NIGHT_FREE_OPEN_NM")
    val nightFreeOpenName: String,

    @JsonProperty("WEEKDAY_BEGIN_TIME")
    val weekdayBeginTime: String,

    @JsonProperty("WEEKDAY_END_TIME")
    val weekdayEndTime: String,

    @JsonProperty("WEEKEND_BEGIN_TIME")
    val weekendBeginTime: String,

    @JsonProperty("WEEKEND_END_TIME")
    val weekendEndTime: String,

    @JsonProperty("HOLIDAY_BEGIN_TIME")
    val holidayBeginTime: String,

    @JsonProperty("HOLIDAY_END_TIME")
    val holidayEndTime: String,

    @JsonProperty("SATURDAY_PAY_YN")
    val saturdayPayYN: String,

    @JsonProperty("SATURDAY_PAY_NM")
    val saturdayPayName: String,

    @JsonProperty("HOLIDAY_PAY_YN")
    val holidayPayYN: String,

    @JsonProperty("HOLIDAY_PAY_NM")
    val holidayPayName: String,

    @JsonProperty("FULLTIME_MONTHLY")
    val fullTimeMonthly: String,

    @JsonProperty("GRP_PARKNM")
    val groupParkName: String?,

    @JsonProperty("RATES")
    val rates: Double,

    @JsonProperty("TIME_RATE")
    val timeRate: Double,

    @JsonProperty("ADD_RATES")
    val addRates: Double,

    @JsonProperty("ADD_TIME_RATE")
    val addTimeRate: Double,

    @JsonProperty("BUS_RATES")
    val busRates: Double,

    @JsonProperty("BUS_TIME_RATE")
    val busTimeRate: Double,

    @JsonProperty("BUS_ADD_RATES")
    val busAddRates: Double,

    @JsonProperty("BUS_ADD_TIME_RATE")
    val busAddTimeRate: Double,

    @JsonProperty("DAY_MAXIMUM")
    val dayMaximum: Double,

    @JsonProperty("LAT")
    val latitude: Double,

    @JsonProperty("LNG")
    val longitude: Double,

    @JsonProperty("SH_CO")
    val shCo: String,

    @JsonProperty("SH_LINK")
    val shLink: String,

    @JsonProperty("SH_TYPE")
    val shType: String,

    @JsonProperty("SH_ETC")
    val shEtc: String
){

    val supportMonthly:String = if (fullTimeMonthly == "0" || fullTimeMonthly == ""){
        "-"
    }
    else{
        fullTimeMonthly.toInt().toString()
    }
    val payment = "평일: $payName / 토요일: $saturdayPayName / 주말&공휴일: $holidayPayName"
    val pay = "기본 주차 요금 : ${timeRate.toInt()}분당 ${rates.toInt()} 원\n" +
            "추가 요금 : ${addTimeRate.toInt()}분당 ${addRates.toInt()} 원\n" +
            "월 정기권 : ${supportMonthly}원"
    val businessHours =
        "운영시간\n평일 : ${
        weekdayBeginTime.substring(
            0,
            2
        )
    }:${weekdayBeginTime.substring(2)} - ${
        weekdayEndTime.substring(
            0,
            2
        )
    }:${weekdayEndTime.substring(2)}\n" +
        "주말 : ${
            weekendBeginTime.substring(
                0,
                2
            )
        }:${weekendBeginTime.substring(2)} - ${
            weekendEndTime.substring(
                0,
                2
            )
        }:${weekendEndTime.substring(2)}\n" +
        "공휴일 : ${
            holidayBeginTime.substring(
                0,
                2
            )
        }:${holidayBeginTime.substring(2)} - ${
            holidayEndTime.substring(
                0,
                2
            )
        }:${holidayEndTime.substring(2)}\n"

}

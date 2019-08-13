package com.example.repairongo;

public class submit_query {
    String problem_title;
    String problem_description;
    String booking_id;
    String user_ID;
    String response;
    String spid;
    String bookingcost;
    String status;
    String bookingdate;
    String checker;
    String sp_checker;

    public submit_query(){}

    public submit_query(String booking_id,String user_ID,String spid,String problem_title,String problem_description,String response,String bookingdate,String bookingcost,String status,String checker,String sp_checker) {
        this.problem_title = problem_title;
        this.problem_description = problem_description;
        this.booking_id = booking_id;
        this.user_ID = user_ID;
        this.response = response;
        this.spid = spid;
        this.bookingcost = bookingcost;
        this.status = status;
        this.bookingdate = bookingdate;
        this.checker=checker;
        this.sp_checker=sp_checker;
    }

    public String getProblem_title() {
        return problem_title;
    }

    public String getProblem_description() {
        return problem_description;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public String getResponse() {
        return response;
    }

    public String getSpid() {
        return spid;
    }

    public String getBookingcost() {
        return bookingcost;
    }

    public String getStatus() {
        return status;
    }

    public String getBookingdate() {
        return bookingdate;
    }

    public String getChecker() {
        return checker;
    }

    public String getSp_checker() {
        return sp_checker;
    }
}


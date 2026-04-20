<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Medical Appointment Form</title>

<style>
    *{
        margin:0;
        padding:0;
        box-sizing:border-box;
        font-family: Arial, Helvetica, sans-serif;
    }

    body{
        background: #0b3d5c;
        min-height: 100vh;
        color: white;
    }

    .container{
        width: 85%;
        max-width: 950px;
        margin: 40px auto;
        background: rgba(255,255,255,0.08);
        padding: 35px 45px;
        border-radius: 10px;
    }

    h1, h2{
        text-align:center;
        margin-bottom: 20px;
        letter-spacing: 2px;
    }

    .row{
        display:flex;
        gap:30px;
        margin-bottom:18px;
    }

    .col{
        flex:1;
    }

    label{
        display:block;
        margin-bottom:7px;
        font-weight:bold;
    }

    input, select{
        width:100%;
        padding:10px;
        border:none;
        outline:none;
        border-radius:5px;
        font-size:14px;
    }

    .btn-area{
        text-align:center;
        margin-top:20px;
    }

    .btn{
        background:#f39c12;
        color:white;
        border:none;
        padding:12px 25px;
        font-size:15px;
        border-radius:5px;
        cursor:pointer;
        font-weight:bold;
    }

    .message{
        text-align:center;
        margin-top:15px;
        font-weight:bold;
        color: #ffd700;
    }

    @media(max-width:768px){
        .row{
            flex-direction:column;
        }
    }
</style>
</head>
<body>

<div class="container">
    <h1>MEDICAL APPOINTMENT FORM</h1>
    <h2>MAKE AN APPOINTMENT</h2>

    <form action="<%=request.getContextPath()%>/AppointmentServlet" method="post">
        <div class="row">
            <div class="col">
                <label>Patient Name</label>
                <input type="text" name="pname" required>
            </div>
            <div class="col">
                <label>Select Date</label>
                <input type="date" name="app_date" required>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <label>Phone Number</label>
                <input type="text" name="phone" pattern="[0-9]{10}" title="Phone number must be 10 digits" required>
            </div>
            <div class="col">
                <label>Department</label>
                <select name="department" required>
                    <option value="">Select Department</option>
                    <option>Cardiology</option>
                    <option>Neurology</option>
                    <option>Pediatrics</option>
                    <option>Orthopedics</option>
                    <option>General Medicine</option>
                </select>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <label>Email</label>
                <input type="email" name="email" required>
            </div>
            <div class="col">
                <label>Gender</label>
                <select name="gender" required>
                    <option value="">Select Gender</option>
                    <option>Male</option>
                    <option>Female</option>
                    <option>Other</option>
                </select>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <label>Symptoms</label>
                <input type="text" name="symptoms" required>
            </div>
            <div class="col">
                <label>Time</label>
                <input type="time" name="app_time" required>
            </div>
        </div>

        <div class="btn-area">
            <button type="submit" class="btn">Make an Appointment</button>
        </div>
    </form>

    <div class="message">
        <%
            String message = (String) request.getAttribute("message");
            if(message != null){
                out.print(message);
            }
        %>
    </div>
</div>

</body>
</html>
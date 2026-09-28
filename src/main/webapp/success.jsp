<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration successful</title>
    <!-- Nhúng file CSS -->
    <link rel="stylesheet" href="main.css" type="text/css">
</head>
<body>
    <h1>Thanks for joining our email list!</h1>
    <p>Hello <b>${user.firstName} ${user.lastName}</b>, you have been registered successfully.</p>
    <p>A confirmation email has been sent to <b>${user.email}</b>.</p>
    <br>
    <p><a href="index.jsp">Register another email address</a></p>
</body>
</html>

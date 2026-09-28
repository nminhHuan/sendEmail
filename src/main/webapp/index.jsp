<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="main.css" type="text/css">
</head>
<body>
    <h1>Join our email list</h1>
    <p>To join our email list, enter your name and email address below</p>
    <form action="emailList" method="post">
        <table>
            <tr>
                <td>Email:</td>
                <td><input type="email" name="email" required></td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td><input type="text" name="firstName" required></td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td><input type="text" name="lastName" required></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Join Now"></td>
            </tr>
        </table>
    </form>
</body>
</html>

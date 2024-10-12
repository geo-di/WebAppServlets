<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-widdth , initial-scale=1.0">
        <link rel="stylesheet" href="contactpro.css">
        <title>Contact</title>
    </head>

    <body>

        <div class="container">
            <div class=" navbar">
                <img src="media/logopro.png" class="logo">
                <ul>
                    <li><a href="#" >Home</a></li>
                    <li><a href="#" >About Us</a></li>
                    <li><a href="#" >Users</a></li>
                    <li><a href="#" >Packages</a></li>
                    <li><a href="#" >Contact</a></li>
                    <li><a href="login.jsp"><i class="fa fa-user" style="font-size:36px"></i></a></li>

                </ul>
            </div>
        </div>


        <div class="contact-container">
            <form action="" class="contact-left">
                <div class="contact-left-title">
                    <h2>GET IN TOUCH</h2>

                    <hr>
                </div>
                <input type="text" name="name" placeholder="Name" class="contact-inputs" required>
                <input type="email" name="Email" placeholder="Email" class="contact-inputs" required>
                <textarea name="message" placeholder="Your Message"  class="contact-inputs" required></textarea>
                <button type="submit">Submit</button>

            </form>
            <div class="contact-right">
                
            </div>
        
    </body>
</html>
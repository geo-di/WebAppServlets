<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
    <head>
        <title>Log-in</title>
        <link rel="stylesheet" href="log.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://kit.fontawesome.com/c5f1eac028.js" crossorigin="anonymous"></script>
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
                    <li><a href="contact.jsp" >Contact</a></li>
                    <li><a href="login.jsp"><i class="fa fa-user" style="font-size:36px"></i></a></li>

                </ul>
            </div>


            <div class="content">
                <div class="form-box">
                    <h1 id="title">Sign Up</h1>
                    <form class="loginform" action="LoginServlet" method="post">
                    <div class="input-group">
                        <div class="input-field" id="nameField">
                            <i class="fa-solid fa-user" style="color: #2b1e4a;"></i>
                            <input type="text" name="firstname" id="firstname" placeholder="First Name" required>
                            <input type="text" name="lastname" id="lastname" placeholder="Last Name" required>
                        </div>

                        <div class="input-field" >
                            <i class="fa-solid fa-square-envelope" style="color: #2b1e4a;"></i>
                            <input type="text" name="username" placeholder="Username" required>
                        </div>

                        <div class="input-field">
                            <i class="fa-solid fa-lock" style="color:#2b1e4a;"></i>
                            <input type="password" name="password" placeholder="Password" required>
                        </div>
                        <p>Forgot Password <a href="#">Click Here</a></p>
                    </div>
                    <input type="hidden" name="action" id="action" value="">
                    
                    <div class="btn-field">
                        <button type="submit" id="signupbtn"><span></span>SIGN UP</button><br>
                        <button type="submit" id="signinbtn"class="disable"><span></span>SIGN IN</button>

                    </div>
                    
                    <%
      					session.removeAttribute("logged");
						if(session.getAttribute("error")!=null){
						out.println(session.getAttribute("error"));
						}
	   				%>
                     </form>

                    
                </div>
            </div>
        </div>

        <section class="footer">
            
            <h1 class="footerH1">UNIPI TELECOM</h1>
            <div class="footerAM">
                <ul>
                    <li><a href="#" >P20105</a></li>
                    <li><a href="#" >P20040</a></li>
                    <li><a href="#" >P20207</a></li>
                </ul>

            </div>
           

        </section>


        <script>
            let signupbtn= document.getElementById("signupbtn");
            let signinbtn = document.getElementById("signinbtn");
            let nameField = document.getElementById("nameField");
            let title= document.getElementById("title");
            let firstName = document.getElementById("firstname");
            let lastName = document.getElementById("lastname");



            signinbtn.onclick = function(){
                nameField.style.maxHeight="0";
                title.innerHTML="Sign In";
                signupbtn.classList.add("disable");
                signinbtn.classList.remove("disable");
                firstName.required = false;
                lastName.required = false;
                if (document.getElementById('action').value !== 'signin') {
                    document.getElementById('action').value = 'signin';
                    return false; 
                } else {
                	document.getElementById("firstname").value = "";
                	document.getElementById("lastname").value = "";
                }
            }

            signupbtn.onclick = function(){
                nameField.style.maxHeight="60px";
                title.innerHTML="Sign Up";
                signupbtn.classList.remove("disable");
                signinbtn.classList.add("disable");
                firstName.required = true;
                lastName.required = true;
                if (document.getElementById('action').value !== 'signup') {
                    document.getElementById('action').value = 'signup';
                    return false; 
                }
            }
            
          </script>
    </body>
</html>
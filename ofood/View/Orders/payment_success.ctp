<html>
    <head>
        <style>
            body{
                background-color: #FA5858;
                color:#fff;
            }
            input{
                background-color: #F7D358;
                width: 300px;
                padding:10px;
                color: #000;
            }
            div#content{
                padding:20px;
                background-color: #F7D358;
                color: #000;
            }
        </style>
        <script type="text/javascript">
            function moveToScreenTwo() {
                Android.moveToNextScreen();
            }
        </script>
    </head>
    <body>
    <center>
        <h3>Binding JavaScript code to Android code</h3>
        <div id="content">
            When developing a web application that's designed specifically for the WebView in your Android application, you can create interfaces between your JavaScript code and client-side Android code. For example, your JavaScript code can call a method in your Android code to display a Dialog, instead of using JavaScript's alert() function.
        </div>
        <div>
            Here are few examples:
        </div>
        <div>
            <input type="button" value="Make Toast" onClick="showAndroidToast('Toast made by Javascript :)')" /><br/>
            <input type="button" value="Trigger Dialog" onClick="showAndroidDialog('This dialog is triggered by Javascript :)')" /><br/>
            <input type="button" value="Take me to Next Screen" onClick="moveToScreenTwo()" />
        </div>
    </center>
</body>
</html>
<#macro login path>
    <form action="${path}"  method="post">
        Username <input type="text" name="username"/>
        <br>
        Password <input type="hidden" name="password">
        <br>
        <button type="submit">sign in</button>
    </form>
</#macro>
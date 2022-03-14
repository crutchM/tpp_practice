<#macro mkdir path, token>
    <div style="padding-left: 20px">
        create new dir
        <form action="/file/mkdir" method="post">
            <input type="text" name="name">
            <br>
            <input type="hidden" name="path" value="${path}">
            <input type="hidden" name="_csrf" value="${ token }"/>
            <br>
            <button type="submit">mkdir</button>
        </form>
    </div>
</#macro>
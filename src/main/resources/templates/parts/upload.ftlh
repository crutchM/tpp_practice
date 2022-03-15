<#macro upload path, token, mode>
    <div style="padding-left: 20px;">
        <form method="post" enctype="multipart/form-data" action="/file/upload">
            <input type="hidden" name="path" value="${path}">
            <input type="hidden" name="mode" value="${mode}">
            <input type="hidden" name="_csrf" value="${ token }"/>
            File to upload <input type="file" name="attachment">
            <br>
            <input type="text" name="name">
            <br>
            <button type="submit"> upload</button>
        </form>


    </div>
</#macro>
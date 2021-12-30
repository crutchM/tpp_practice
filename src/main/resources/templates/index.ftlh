<#import "parts/page.ftlh" as c>

<@c.page "main page">
    <h1>${path}</h1>
    <div style="display: flex">
        <div style="background-color: aqua; width: 700px; height: 700px">
            <#if files?size == 0>
                <table>

                </table>
                <#else>
                    <table>

                            <tr>
                                <#list files as f>
                                    <td style="height: 100px; width: 100px">
                                            <#if f.getExtension() == "png" || f.getExtension() == "jpeg">
                                                <img style="max-height: 50px; max-width: 50px" src="https://cdn.onlinewebfonts.com/svg/download_277376.png">
                                                <#elseif f.getExtension() == "txt">
                                                <img style="max-height: 50px; max-width: 50px" src="https://www.pinclipart.com/picdir/big/125-1252450_txt-icon-free-download-png-and-vector-txt.png">
                                                <#elseif f.getExtension() == "folder">
                                                <img style="max-height: 50px; max-width: 50px" src="https://avatars.mds.yandex.net/i?id=3fd8ab7cbc11e6dd5b1e3a51fe0ad1ac-5520044-images-thumbs&n=13&exp=1">
                                            </#if>
                                            <br>
                                            <#if f.getExtension() != "folder">
                                                <a href="/file/${f.getId()}">
                                                    ${f.getName()}
                                                </a>
                                                <#elseif f.getPath() == "/">
                                                <a href="/getFiles?path=${f.getPath()}${f.getName()}">
                                                    ${f.getName()}
                                                </a>
                                                <#elseif f.getPath() != "/">
                                                <a href="/getFiles?path=${f.getPath()}${f.getName()}">
                                                ${f.getName()}
                                               </a>
                                            </#if>
                                            <br>
                                            <a href="/file/delete/${f.getId()}">
                                                delete
                                            </a>
                                    </td>
                                </#list>
                            </tr>

                    </table>
            </#if>
        </div>
        <div style="padding-left: 20px">
            create new dir
            <form action="/file/mkdir" method="post">
                <input type="text" name="name">
                <br>
                <input type="hidden" name="path" value="${path}">
                <input type="hidden" name="_csrf" value="${ _csrf.token }"/>
                <br>
                <button type="submit">mkdir</button>
            </form>
        </div>
        <div style="padding-left: 20px;">
            <form method="post" enctype="multipart/form-data" action="/file/upload">
                <input type="hidden" name="path" value="${path}">
                <input type="hidden" name="_csrf" value="${ _csrf.token }"/>
                <br>
                File to upload <input type="file" name="attachment">
                <br>
                <input type="text" name="name">
                <br>
                <button type="submit"> upload</button>
            </form>


        </div>
    </div>

</@c.page>
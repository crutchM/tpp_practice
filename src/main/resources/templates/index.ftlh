<#import "parts/page.ftlh" as c>

<@c.page "main page">


    <div style="display: flex">
        <div style="background-color: aqua">
            <#if files.isEmpty()>
                <table>

                </table>
                <#else>
                    <table>
                        <#list files as file>
                            <tr>
                                <#list file as f>
                                    <td style="height: 100px; width: 100px">
                                        <p>
                                            <#if f.getExtension() == "png" || f.getExtension() == "jpeg">
                                                <img src="https://cdn.onlinewebfonts.com/svg/download_277376.png">
                                                <#elseif f.getExtension() == "txt">
                                                <img src="https://www.pinclipart.com/picdir/big/125-1252450_txt-icon-free-download-png-and-vector-txt.png">
                                                <#elseif f.getExtension() == "folder">
                                                <img src="https://avatars.mds.yandex.net/i?id=3fd8ab7cbc11e6dd5b1e3a51fe0ad1ac-5520044-images-thumbs&n=13&exp=1">
                                            </#if>
                                        </p>
                                        <p>
                                            <#if f.getExtension() != "folder">
                                                <a href="/file/${f.getId()}">
                                                    ${f.getName()}
                                                </a>
                                                <#else>
                                                <a href="/getFiles?path=${f.getPath()}/${f.getName()}">
                                                    ${f.getName()}
                                                </a>
                                            </#if>
                                        </p>
                                        <p>
                                            <a href="/file/delete/${f.getId()}">
                                                delete
                                            </a>
                                        </p>
                                    </td>
                                </#list>
                            </tr>
                        </#list>
                    </table>
            </#if>
        </div>
        <div style="padding-left: 20px">
            <form action="/file/mkdir" method="post">
                <input type="text" name="name">
                <br>
                <input type="text" name="path">
                <br>
                <button type="submit">mkdir</button>
            </form>
        </div>
    </div>

</@c.page>
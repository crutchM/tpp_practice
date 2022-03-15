<#import "parts/page.ftlh" as p>
<#import "parts/mkdir.ftlh" as mk>
<#import "parts/upload.ftlh" as upl>

<@p.page "main page">
    <h1>${path}</h1>
    <#if path == "/">
        <h1>Вы в корневом каталоге, дальше некуда</h1>
        <#else>
            <h1><a href="/getFiles?path=${path}&mode=1&up=0">UP</a></h1>
    </#if>

    <div style="display: flex">
        <div style="width: 500px; background-color: aqua">
            <form action="/getFiles">
                <input type="hidden" name="path" value="${path}">
                <input type="hidden" name="up" value="1">
                <select name="mode">
                    <option value="1">без сортировки</option>
                    <option value="2">дата: по возрастанию</option>
                    <option value="3">дата: по убыванию</option>
                    <option value="4">имя: алфавитный порядок</option>
                    <option value="5">имя: наоборот</option>
                    <option value="6">размер: по возрастанию</option>
                    <option value="7">размер: по убыванию</option>
                </select>
                <br>
                <button type="submit">отсортировать</button>
            </form>
            <#if listSize == 0>
                <table>

                </table>
                <#else>
                    <table>
                        <#list files as f>
                        <tr>
                            <td><a>${f.getId()}</a></td>
                            <td style="height: 100px; width: 100px">
                                <#if f.getExtension() == "png" || f.getExtension() == "jpeg">
                                    <img style="max-height: 50px; max-width: 50px" src="https://cdn.onlinewebfonts.com/svg/download_277376.png">
                                <#elseif f.getExtension() == "txt">
                                    <img style="max-height: 50px; max-width: 50px" src="https://www.pinclipart.com/picdir/big/125-1252450_txt-icon-free-download-png-and-vector-txt.png">
                                <#elseif f.getExtension() == "folder">
                                    <img style="max-height: 50px; max-width: 50px" src="https://avatars.mds.yandex.net/i?id=3fd8ab7cbc11e6dd5b1e3a51fe0ad1ac-5520044-images-thumbs&n=13&exp=1">
                                </#if>
                            </td>
                            <td style="height: 100px; width: 100px">
                                <#if f.getExtension() != "folder">
                                    <a href="/file/${f.getId()}">
                                        ${f.getName()}
                                    </a>
                                <#elseif f.getPath() == "/">
                                    <a href="/getFiles?path=${f.getPath()}${f.getName()}&mode=${mode}&up=1">
                                        ${f.getName()}
                                    </a>
                                <#elseif f.getPath() != "/">
                                    <a href="/getFiles?path=${f.getPath()}${f.getName()}&mode=${mode}&up=1">
                                        ${f.getName()}
                                    </a>
                                </#if>
                                <br>
                                <a href="/file/delete/${f.getId()}">delete</a>
                            </td>
                        </tr>
                        </#list>
                    </table>
            </#if>
        </div>
        <div>
            <div>
            <@mk.mkdir "${path}", "${_csrf.token}">

            </@mk.mkdir>
            <br>
            <@upl.upload "${path}", "${_csrf.token}", "${mode}">

            </@upl.upload>
            </div>
            <br>
            <div style="padding-left: 20px">
                <br>
                <form action="/file/update" method="post">
                    <input type="number" name="id">
                    <br>
                    <input type="text" name="newName">
                    <input type="hidden" name="path" value="${path}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <br>
                    <button type="submit">update chosen file</button>
                </form>
                <form action="/file/move" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="number" name="id">
                    <br>
                    <a>вы можете перемщать файл только на уровень выше/ниже, если хотите подняться наверх укажите up</a>
                    <br>
                    <input type="text" name="path">
                    <br>
                    <button type="submit">move</button>
                </form>
            </div>
        </div>

    </div>
</@p.page>
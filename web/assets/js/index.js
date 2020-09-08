var xhr = new XMLHttpRequest();
var dataXmlString;
var domParser = new DOMParser();

getDataFromServer();
function getDataFromServer() {
    xhr.open('GET', path + "/FirstController", true);
    xhr.send();
    xhr.onreadystatechange = function () {
        if (xhr.status != 200) { // analyze HTTP status of the response
            alert('Error ${xhr.status}: ${xhr.statusText}'); // e.g. 404: Not Found
        } else { // show the result
            dataXmlString = xhr.responseText;
            var xmlDOM = domParser.parseFromString(dataXmlString, "application/xml");
            console.log(xmlDOM.)
            return  xmlDOM;
        }
    };
}


var formOneAccessary = '<div class="w3-content">'
        + '<div class="w3-row w3-margin">'
        + '<div class="w3-third">'
        + '<a href=":detailLink" >'
        + '<img src=":imgLink" style="width:100%;min-height:200px"/>'
        + '</a>'
        + '</div>'
        + '<div class="w3-twothird w3-container">'
        + '<h2>:Name</h2>'
        + '<p>:price</p>'
        + '<p>Tham khảo thêm: <a href=":detailLink">Tại đây</a> </p>'
        + '</div>'
        + '</div>'
        + '</div>';

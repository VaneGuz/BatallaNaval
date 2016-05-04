/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    //
    var loc = document.location.pathname;
    var wsURI = "ws://" + document.location.host + loc.substring(0, loc.lastIndexOf('/')) + "/servidorBatalla";
    var webSocket = new WebSocket(wsURI);

    webSocket.onopen = function (evt) {
        onOpen(evt);
    };

    webSocket.onmessage = function (evt) {
        onMessage(evt);
    };

    webSocket.onerror = function (evt) {
        onError(evt);
    };
    function onOpen(evt) {

    }
    ;
    function onMessage(evt) {
        $('#cuerpo').empty();
        $('#cuerpo').append(evt.data);

    }
    ;
    function onError(evt) {

    }
    ;
    $('#boton').click(function () {
        webSocket.send("nuevo");
    });

    $('body').on('click', 'td', function (evt) {
        //alert(evt.target.attr('cx'));
        //alert('x: ' + jQuery(this).attr("cx") + ' y: ' + jQuery(this).attr("cy"));
        webSocket.send(jQuery(this).attr("cx") + " " + jQuery(this).attr("cy"));
    });
});
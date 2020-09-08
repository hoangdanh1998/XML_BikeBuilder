<%-- 
    Document   : navbar
    Created on : Jul 10, 2020, 1:57:39 PM
    Author     : apple
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h2 class="py-20 text-center ls-10 select-none banner" style="font-size:24px">xedap</h2>
<div>
    <ul class="flex flex-row shadow nav">
        <%
            final String[] navNames = {
                "TRANG CHỦ",
                "PHONG THUỶ",
                "TRA CỨU",};

            final String[] navIds = {
                "nav-home",
                "nav-phongthuy",
                "nav-look-up",};

            final String[] navLinks = {
                "index.jsp",
                "phongthuy.jsp",
                "look-up.jsp",};

                    for (int i = 0; i < navNames.length; ++i) {%>
        <a class="flex-1" href="<%= navLinks[i]%>">
            <li id="<%= navIds[i]%>" class="text-center p-10 upper-case transition text-14 text-bold nav-item cursor-pointer">
                <%= navNames[i]%>
            </li>
        </a>
        <%}%>
</div>

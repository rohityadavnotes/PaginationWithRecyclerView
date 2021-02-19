package com.pagination.with.recyclerview.remote.exception;

public class HTTPResponseStatusCodes  {

    /*
     * All HTTP response status codes are separated into five classes or categories.
     *
     * Informational responses (100–199), the request was received, continuing process
     * Successful responses (200–299), the request was successfully received, understood, and accepted
     * Redirects (300–399), further action needs to be taken in order to complete the request
     * Client errors (400–499), the request contains bad syntax or cannot be fulfilled
     * and Server errors (500–599), the server failed to fulfil an apparently valid request
    */

    /*
     * Gets info.
     *
     * @param statusCode the status code
     * @return the info
    */
    public static ExceptionDetails getCodeInfo(int statusCode)
    {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setCode(statusCode);

        switch (statusCode) {
            case 100: {
                exceptionDetails.setTitle("Informational Response");
                exceptionDetails.setMessage("Continue, The server has received the request headers, and the client should proceed to send the request body.");
                exceptionDetails.setDescription("When a client gets this status code, it means the server has received their request header and has accepted the request, so the client can go ahead and send the request body. This is most commonly used when a client wants to send content that is large. It will send an Expect: 100 - continue to the server and when the server sends back a response with status 100 continue, it proceeds to send the body. The status 100 continue received from the server means “You can now send more data or ignore if you are done sending”. In some cases, a client might send Expect: 100 - continue along with the request body. This is most common with curl requests as it is the default mode curl communicates with servers.");
                break;
            }
            case 101: {
                exceptionDetails.setTitle("Informational Response");
                exceptionDetails.setMessage("The server switches protocol.");
                exceptionDetails.setDescription("A client can send a request to the server to switch the communication protocol using the Upgrade header. This can be say switching from HTTP/1.1 to HTTP/2 or switching to WebSocket. The server will response with a response code 101 and an Upgrade response header with information on the protocol it upgraded to.");
                break;
            }
            case 200: {
                exceptionDetails.setTitle("Success Response");
                exceptionDetails.setMessage("OK, The request is OK (this is the standard response for successful HTTP requests).");
                exceptionDetails.setDescription("This response status code indicates that our request was successful. This is used mostly when we request for data from the server and it responds with the data. When you visit the link to a webpage, the browser sends a request to the server to give it the contents of that webpage. The server will respond with a status 200 ok and a header specifying the type of content returned (text/html, multimedia, etc) and a body containing the content itself.");
                break;
            }
            case 201: {
                exceptionDetails.setTitle("Success Response");
                exceptionDetails.setMessage("Created, The request has been fulfilled, and a new resource is created.");
                exceptionDetails.setDescription("This is the response code the client gets after sending resource (data) to the server. This resource is stored by the server and upon successfully storing it, the 201 Created response code is returned with the newly created resource as the request body. It can be form submissions, file uploads or other related activities.");
                break;
            }
            case 202: {
                exceptionDetails.setTitle("Success Response Accepted");
                exceptionDetails.setMessage("Accepted, The request has been accepted for processing, but the processing has not been completed.");
                exceptionDetails.setDescription("This is not a very common response code sent by servers. It is used in cases where a request by the client has been received but the server has sent it in for processing. It is a non-committal response in that when the server eventually comes around to process the request, it may or may not act upon it based on if the client has the right to make that request or if the server has the means to handle it. Also, it means the server will not be sending any response afterwards. This can be used in cases when a request is transferred to another server or when a request is batched to be proceed at another time. In such a scenario, the server ought to return an indicator of the current status of the request and a way for the client to monitor the processing of the request.");
                break;
            }
            case 203: {
                exceptionDetails.setTitle("Success Response");
                exceptionDetails.setMessage("Non-Authoritative Information, The request has been successfully processed, but is returning information that may be from another source.");
                exceptionDetails.setDescription("This is also not a very common response code. It signifies that the response the client is getting is not exactly as it was when sent by the server. It can mean that the response has been modified as it passed through a proxy tunnel or some other related third party. The data eventually returned might be a subset or superset of the data returned from the server.");
                break;
            }
            case 204: {
                exceptionDetails.setTitle("Success Response");
                exceptionDetails.setMessage("No Content, The request has been successfully processed, but is not returning any content.");
                exceptionDetails.setDescription("This response code tells the client (in the case of a user agent) not to change the current document presented to the user. Header information might be updated, but no new content will be sent along. This response can be sent after a client makes a request updating a resources on the server and the server does not need to return any data since nothing new was created. The server must never return a response body when it sends a 204 - No Content status code.");
                break;
            }
            case 205: {
                exceptionDetails.setTitle("Success Response");
                exceptionDetails.setMessage("Reset Content, The request has been successfully processed, but is not returning any content, and requires that the requester reset the document view");
                exceptionDetails.setDescription("This response status tells the client to refresh the document sample.");
                break;
            }
            case 206: {
                exceptionDetails.setTitle("Success Response");
                exceptionDetails.setMessage("Partial Content, The server is delivering only part of the resource due to a range header sent by the client.");
                exceptionDetails.setDescription("This response code indicates that the request has succeeded and the response body has the requested ranges of data. The server only sends ranges of data when the client sets the Range header in it’s request. Bear in mind that the client must never request a range if it cannot handle the range. If there is only one range, the Content-Type of the whole response is set to the type of the document, and a Content-Range is provided. If several ranges are sent back, the Content-Type is set to multipart/byteranges and each fragment cover one range, with Content-Range and Content-Type describing it. When a range is requested by the client, the server returns 206 Parital Content and never returns a 200 Ok. Medias like large videos and images are good examples of data return as a range.");
                break;
            }
            case 300: {
                exceptionDetails.setTitle("Redirection Response");
                exceptionDetails.setMessage("Multiple Choices, A link list. The user can select a link and go to that location. Maximum five addresses.");
                exceptionDetails.setDescription("This status code means the request has more than one possible response. The client is to choose one of them. There is no standardized way of choosing so this is rarely used. In case you see it, look for the Location header which usually contains the servers preferred choice.");
                break;
            }
            case 301: {
                exceptionDetails.setTitle("Redirection Response");
                exceptionDetails.setMessage("Moved Permanently, The requested page has moved to a new URL Permanently.");
                exceptionDetails.setDescription("This is arguably the most important of the redirection status codes. When not used properly, it can interfere with your SEO and bury your website forever. It can also created very bad user experience and increase the churn you experience on your website. This tells the client that the resource they seek has been moved permanently, and then presents the URL to the new location of the resource. This does two things: tells the client where to find the resource and also helps the client know where to go the next time they need the resource. The new location for the resource is specified by the Location header.");
                break;
            }
            case 302: {
                exceptionDetails.setTitle("Redirection Response");
                exceptionDetails.setMessage("Found, The requested page has moved temporarily to a new URL.");
                exceptionDetails.setDescription("This is the direct sibling of 301 \uD83D\uDE01. It is used for temporary redirect. Client browsers will redirect to the specified resource but indexing systems like search engines will not change their reference to the resource as the redirect is only temporary. And like 301, client browsers might change the body/method of the request, so when you want to temporarily redirect a POST, use 307 instead.");
                break;
            }
            case 303: {
                exceptionDetails.setTitle("Redirection Response");
                exceptionDetails.setMessage("See Other, The requested page can be found under a different URL.");
                exceptionDetails.setDescription("Well, we will call this the cousin to 301 and 302 \uD83D\uDE02. Simply put, this status code tells the client that the redirect doesn’t link to the newly uploaded resources but to another page, like a thank you page or status monitor page. It is sent as a result of a PUT or POST request and the method to use for the redirections is always GET. I told you this was the cousin.");
                break;
            }
            case 304: {
                exceptionDetails.setTitle("Redirection Response");
                exceptionDetails.setMessage("Not Modified, Indicates the requested page has not been modified since last requested.");
                exceptionDetails.setDescription("When you have previously fetched a cacheable content, this status code comes in handy. It tells the client that the resource they are trying to fetch has not changed, so they should retain the copy they have. This will come in handy if you are building a system like a newsfeed and you always want to check for new updates. It will prevent you fetching old data and reloading the client browser unnecessarily. A nice option would be to use Pusher’s realtime API.");
                break;
            }
            case 306: {
                exceptionDetails.setTitle("Redirection Response");
                exceptionDetails.setMessage("Switch Proxy, No longer used.");
                exceptionDetails.setDescription("");
                break;
            }
            case 307: {
                exceptionDetails.setTitle("Redirection Response");
                exceptionDetails.setMessage("Temporary Redirect, The requested page has moved temporarily to a new URL.");
                exceptionDetails.setDescription("We have already talked about this earlier that it will be okay to skip it right? Well, this response code is sent by the server when it intends to explicitly tell the client to maintain the method originally used for the request. It works just like 302 except it adds a very clear directive not to change anything. Best thing to use in case you have stubborn clients who always change request methods on redirect \uD83D\uDE44.");
                break;
            }
            case 308: {
                exceptionDetails.setTitle("Redirection Response");
                exceptionDetails.setMessage("Resume Incomplete, Used in the resumable requests proposal to resume aborted PUT or POST requests.");
                exceptionDetails.setDescription("The 308 Permanent Redirect is the direct sibling of 307. And it is the strict version of 301.");
                break;
            }
            case 400: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Bad Request, The request cannot be fulfilled due to bad syntax.");
                exceptionDetails.setDescription("I have to admit, this is my favorite status code \uD83D\uDE02. Every time I get slammed with 400 Bad Request red error on my console, I first look up and ask “What kind of life did I choose?” before I proceed to investigate it. Bad requests occur when the client sends a request with either incomplete data, badly constructed data or invalid data. Many times, it could be the fault of the developer who did not specify properly what kind of data they expect. Be that as it may, it happens because the data you sent to a request is incorrect.");
                break;
            }
            case 401: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Unauthorized, The request was a legal request, but the server is refusing to respond to it. For use when authentication is possible but has failed or not yet been provided.");
                exceptionDetails.setDescription("This response in simple terms means the client needs to authenticate itself before it completes the request. Authentication here could be providing access-token in the case of OAuth or Authorization token in the case of jwt based auth system or even API keys. Anything that the server needs to identify who is making a request has to be sent for the request to be completed.");
                break;
            }
            case 402: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Payment Required, Reserved for future use.");
                exceptionDetails.setDescription("");
                break;
            }
            case 403: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Forbidden, The request was a legal request, but the server is refusing to respond to it.");
                exceptionDetails.setDescription("This error occurs when a client tries to access a resource it is not permitted to. This is not the same as 401 Unauthorized (just see them as fraternal twins \uD83D\uDE03). An authenticated client can be Forbidden from accessing a resource just as an unauthenticated client can. Many times, the client only gets 403 Forbidden after it has been authenticated, as the system will have to ensure who the client is first before forbidding or granting them access to the resources.");
                break;
            }
            case 404: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Not Found, The requested page could not be found but may be available again in the future.");
                exceptionDetails.setDescription("If you have used the web frequently, you will definitely have run into this, especially 404 Page Not Found. In API terms, it means the resource you are trying to access was not found or the endpoint itself does not exist. A description of the error might accompany the error, but do not count on this in most cases. 404 does not specify if the resource is missing or has been permanently removed (deleted). In a case where the resource has been removed permanently, the server should return 410 GONE.");
                break;
            }
            case 405: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Method Not Allowed, A request was made of a page using a request method not supported by that page.");
                exceptionDetails.setDescription("This response code results when you try to access a resource designed for only GET requests through a POST request and vice versa. Some resources can be accessed via any request method (GET, POST or HEAD) and in such a case, you will not get the 405 Method Not Allowed response code. Standard practice is that when a server sends a 405 response code, it includes a list of methods supported for accessing the resource in question.");
                break;
            }
            case 406: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Not Acceptable, The server can only generate a response that is not accepted by the client.");
                exceptionDetails.setDescription("This is a rarely used error code. It indicates that the server cannot produce a response that matches the request the user made, and the server is not willing to send a default response. You can learn more about it on Mozilla Developer Docs.");
                break;
            }
            case 407: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Proxy Authentication Required, The client must first authenticate itself with the proxy.");
                exceptionDetails.setDescription("This will be the twin of 401 Unauthorized. The only difference is that authentication needs to be done by a proxy.");
                break;
            }
            case 408: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Request Timeout, The server timed out waiting for the request.");
                exceptionDetails.setDescription("This response code is sent by the server when it wants to close an idle connection opened by the client. The client may not have completed its request and may be taking so much time doing it. The standard is that a server will send a close Connection header in the response field along with the response code. In many cases, the server might shut down the connection without sending any response code.");
                break;
            }
            case 409: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Conflict, The request could not be completed because of a conflict in the request.");
                exceptionDetails.setDescription("This response is sent by the server when a request conflicts with the servers internal operations. A good example of such conflict is trying to update a resource on the server with an older version.");
                break;
            }
            case 410: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Gone, The requested page is no longer available.");
                exceptionDetails.setDescription("We already mentioned that this status code shows that the resource the client wishes to access has been permanently deleted.");
                break;
            }
            case 411: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Length Required, The \"Content-Length\" is not defined. The server will not accept the request without it.");
                exceptionDetails.setDescription("The server returns this status code if it requires that Content-Length header is set along with the request, and the client did not set it.");
                break;
            }
            case 412: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Precondition Failed, The precondition given in the request evaluated to false by the server.");
                exceptionDetails.setDescription("This happens when the client is being too demanding and the server does not have that kind of strength \uD83D\uDE44. So, clients can send conditional requests to servers, which is excellent. If the conditions are met, the server will response with data. If the conditions are not met, the server will just respond with 412 Precondition Failed. We will discuss more on conditional requests when we get to 428 Precondition Required.");
                break;
            }
            case 413: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Request Entity Too Large or Payload Too Large, The server will not accept the request, because the request entity is too large.");
                exceptionDetails.setDescription("The request data is too big for the server to handle \uD83D\uDE03. In more technical terms, the size of the payload may have exceeded the limit specified by the server.");
                break;
            }
            case 414: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Request-URI Too Long, The server will not accept the request, because the URL is too long. Occurs when you convert a POST request to a GET request with a long query information.");
                exceptionDetails.setDescription("Just shorten the URL and all will be fine. Realistically, this only occurs when you have a lot of things appended to a URL when constructing a GET request with a lot of parameters.");
                break;
            }
            case 415: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Unsupported Media Type, The server will not accept the request, because the media type is not supported.");
                exceptionDetails.setDescription("This is what it is. The server does not support the media type requested by the client.");
                break;
            }
            case 416: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Requested Range Not Satisfiable, The client has asked for a portion of the file, but the server cannot supply that portion.");
                exceptionDetails.setDescription("The server can’t fulfill the Range specified in the request header. It could mean the Range is requesting more data than the server can give. Think array index out of bounds and you will get the picture.");
                break;
            }
            case 417: {
                exceptionDetails.setTitle("Client Error");
                exceptionDetails.setMessage("Expectation Failed, The server cannot meet the requirements of the Expect request-header field.");
                exceptionDetails.setDescription("When the server cannot meet the expectation contained in the Expect request header field, it sends this response.");
                break;
            }
            case 500: {
                exceptionDetails.setTitle("Server Error");
                exceptionDetails.setMessage("Internal Server Error, A generic error message, given when no more specific message is suitable.");
                exceptionDetails.setDescription("When the server processes the request of the client and runs into a situation it cannot handle, it sends 500 Internal Server Error. These issues can be caused by many things. A service required by the server may not be available. The developer who build the application may have used a package or library and forgot to download it on the server. The developer might have buggy code and the server ran into it. It could be anything preventing the server from completing it’s operation.");
                break;
            }
            case 501: {
                exceptionDetails.setTitle("Server Error");
                exceptionDetails.setMessage("Not Implemented, The server either does not recognize the request method, or it lacks the ability to fulfill the request.");
                exceptionDetails.setDescription("The server sends this code when you have made a request to it with a request method it does not know or have the capacity to resolve. Servers are required to implement only GET and HEAD methods, so you might send a PUT or PATCH request and the server will not be able to handle it. This is not the same as 405 Method Not Allowed because with 405 the server clearly understands the request but cannot respond to it until the method is changed. With 501 the server cannot understand the method used and is therefore incapable of providing any response.");
                break;
            }
            case 502: {
                exceptionDetails.setTitle("Server Error");
                exceptionDetails.setMessage("Bad Gateway, The server was acting as a gateway or proxy and received an invalid response from the upstream server.");
                exceptionDetails.setDescription("The server sends this code when it is acting as a proxy server and/or fetching data from an external resource and it gets an invalid response from that external source.");
                break;
            }
            case 503: {
                exceptionDetails.setTitle("Server Error");
                exceptionDetails.setMessage("Service Unavailable, The server is currently unavailable (overloaded or down).");
                exceptionDetails.setDescription("This is a common server error code that you can get. It means the server might be down and therefore, is not ready handle the request at that time. This might be a result of an ongoing maintenance or an overload on the server. This is a temporal situation, so if you implement caching in your application, you might want to not cache this response.");
                break;
            }
            case 504: {
                exceptionDetails.setTitle("Server Error");
                exceptionDetails.setMessage("Gateway Timeout, The resource was not cache. The server was acting as a gateway or proxy and did not receive a timely response from the upstream server.");
                exceptionDetails.setDescription("The direct sibling to 502 \uD83D\uDE03. The server sends this response code when it is acting as a gateway and the external resource does not provide it with a response in time.");
                break;
            }
            case 505: {
                exceptionDetails.setTitle("Server Error");
                exceptionDetails.setMessage("HTTP Version Not Supported, The server does not support the HTTP protocol version used in the request.");
                exceptionDetails.setDescription("This status code indicates the HTTP version the client used for the request is not supported by the server. Think making a HTTP/1.1 call to a server when the server only deals in HTTP/2 \uD83D\uDE0E.");
                break;
            }
            case 511: {
                exceptionDetails.setTitle("Server Error");
                exceptionDetails.setMessage("Network Authentication Required, The client needs to authenticate to gain network access.");
                exceptionDetails.setDescription("This status code means the client needs to get authenticated on the network it is trying to access before access can be granted. This happens when trying to access a network via proxy, so it can be argued to be a distant cousin of 401 Unauthorized. Where there is a difference is that 401 tells the client directory it needs to be authenticated but 511 means the proxy network cannot access the external resource because of lack of proper authorization.");
                break;
            }
            default:
                break;
        }
        return exceptionDetails;
    }
}

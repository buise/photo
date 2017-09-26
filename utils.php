class  utils{

/**
        * @access static
        * @param  $imagepath String 图片的完整路径
* @param  $host      String 主机如：127.0.0.1
* @param  $port      String 端口号如：20147
        * @return string 解析出的URL
    */
static function deCodeBitMap($imagepath,$host,$port){
    $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP) or die($imagepath." Could not connet server create\n"); // 创建一个Socket
    if(!$socket){
        return "";
    }
    $connection = socket_connect($socket, $host, $port) or die($imagepath." Could not connet server connection\n");    //  连接
    if(!$connection){
        return "";
    }
    socket_write($socket, $imagepath) or die("Write failed\n"); // 数据传送 向服务器发送消息

    $buff = socket_read($socket, 1024, PHP_NORMAL_READ);
    return $buff;
}

}
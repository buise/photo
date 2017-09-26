<?php
include_once("utils.php");
$base64_image_content=$_POST['img'];
if (preg_match('/^(data:\s*image\/(\w+);base64,)/', $base64_image_content, $result))
{
  $type = $result[2];
  $new_file = "./2.{$type}";
  if (file_put_contents($new_file, base64_decode(str_replace($result[1], '', $base64_image_content)))){
    $code=utils::deCodeBitMap("2.png","192.168.46.123",20147);
    echo '{"status":"success","data":"'.trim($code).'"}';
  }else{
    echo '{"status":"write error","data":"NO"}';
  }
}else{
    echo '{"status":"preg error","data":"NO"}';
}
?>
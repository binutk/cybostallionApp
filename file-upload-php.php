<?php 
//on controller api
$request = false;
if(!empty($_POST["request"]))
{
  $request = json_decode($_POST["request"]);
}
else{
    $request = json_decode(file_get_contents("php://input"));
}

//file upload
if(!isset($_FILES["filename"]["name"]))
{
  //get mime by explode
  $mime = explode(".",$_FILES["filename"]["name"]);
  if($mime!=null && count($mime)>0)
  {
    $mime = $mime[count($mime)-1];
  }
  
  if($mime!=null && $mime=="jpg")
  {

    move_uploaded_files($_FILES["filename"]["temp"],"/uploads/profile/sha2(456456).jpg");
  }
  
  
}



?>

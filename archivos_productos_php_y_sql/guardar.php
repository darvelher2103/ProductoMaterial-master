<?php
require_once getcwd(). '/config/database.php';
$db = new PDO("mysql:host=".$dbconfig['hostname'].";dbname=".$dbconfig['database'],$dbconfig['username'],$dbconfig['password']) or die('No se pudo conectar:'.mysql_error());

$INPUT=filter_var_array($_POST,FILTER_SANITIZE_STRING);

//file_put_contents("image.png",base64_decode($INPUT['foto']));
//$imagen = fopen("fotos/".$INPUT['id'], "wb");
//fwrite($imagen, base64_decode($INPUT['foto']));
//fclose($imagen);


$foto = "http://34.207.114.119/fotos/".$INPUT['idfoto'].".jpg";


$sql= "INSERT INTO productos(uuid,urlFoto,codigo,nombre,precio,idfoto) values (?,?,?,?,?,?)";

$stmt = $db->prepare($sql);
$stmt->bindParam(1,$INPUT['id']);
$stmt->bindParam(2,$foto);
$stmt->bindParam(3,$INPUT['codigo']);
$stmt->bindParam(4,$INPUT['nombre']);
$stmt->bindParam(5,$INPUT['precio']);
$stmt->bindParam(6,$INPUT['idfoto']);

//insertar

$success = $stmt->execute();
$stmt->closeCursor();

//responder
$response = array(
'success'=>$success,
'foto'=>$foto
);

header('Content-type: application/json');
echo json_encode($response);

?>
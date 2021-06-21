import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';

class FirebaseApi {
  final FirebaseAuth auth = FirebaseAuth.instance;

  getCurrentUser() {
    return auth.currentUser;
  }

  signIn() async {
    try {
      if (storedUserCredentials!.userData!.correo != "") {
        Map<String, dynamic> user = {
          "id": storedUserCredentials!.userData!.id,
          "email": storedUserCredentials!.userData!.correo,
          "username": storedUserCredentials!.userData!.nombre + " " + storedUserCredentials!.userData!.apellido[0],
          "nombre": storedUserCredentials!.userData!.nombre + " " + storedUserCredentials!.userData!.apellido,
          "documento": storedUserCredentials!.userData!.documento,
        };

        addUserInfoToDB(storedUserCredentials!.userData!.correo, user);
      }
    } catch (err) {
      print(err);
    }
  }

  addUserInfoToDB(String userId, Map<String, dynamic> userInfoMap) {
    FirebaseFirestore.instance.collection("usuarios").doc(userId).set(userInfoMap);
  }

  Future<Stream<QuerySnapshot>> getUserByCorreo(String correo) async {
    return FirebaseFirestore.instance.collection("usuarios").where("email", isEqualTo: correo).snapshots();
  }

  Future sendMessage(String chatRoomId, String messageId, Map<String, dynamic> message) async {
    return FirebaseFirestore.instance.collection("salasDeChat").doc(chatRoomId).collection("chats").doc(messageId).set(message);
  }

  Future updateLastMessage(String chatRoomId, Map<String, dynamic> data) {
    return FirebaseFirestore.instance.collection("salasDeChat").doc(chatRoomId).update(data);
  }

  createChatRoom(String chatRoomId, Map<String, dynamic> data) async {
    final snapShot = await FirebaseFirestore.instance.collection("salasDeChat").doc(chatRoomId).get();

    if (snapShot.exists) {
      return true;
    } else {
      return await FirebaseFirestore.instance.collection("salasDeChat").doc(chatRoomId).set(data);
    }
  }

  Future<Stream<QuerySnapshot>> getMensajesSala(String chatRoomId) async {
    return FirebaseFirestore.instance.collection("salasDeChat").doc(chatRoomId).collection("chats").orderBy("timeStamp", descending: true).snapshots();
  }

  Future<Stream<QuerySnapshot>> getChatRooms(String userName) async {
    return FirebaseFirestore.instance.collection("salasDeChat").orderBy("timeStamp", descending: true).where("usuarios", arrayContains: userName).snapshots();
  }

  Future<QuerySnapshot> getUserInfo(String correo) async {
    return await FirebaseFirestore.instance.collection("usuarios").where("email", isEqualTo: correo).get();
  }
}

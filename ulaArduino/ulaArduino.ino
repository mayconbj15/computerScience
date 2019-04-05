#include<stdlib.h>

//saidas da ula
int F3 = 13;
int F2 = 12;
int F1 = 11;
int F0 = 10;

//entrada
byte next;
int a[4] = {0,0,0,0}; //operando1
int b[4] = {0,0,0,0}; //operando2
int s[4] = {0,0,0,0}; //codigo da operação

int newCode[3] = {0,0,0}; //array temporario para conversão de valores

int i = 0;
int j = 0;

String str = String();

void zeroLogico(){
  digitalWrite(F0, 0);
  digitalWrite(F1, 0);
  digitalWrite(F2, 0);
  digitalWrite(F3, 0);
}

void umLogico(){
  digitalWrite(F0, 1);
  digitalWrite(F1, 1);
  digitalWrite(F2, 1);
  digitalWrite(F3, 1);
}

int* portaNot(int A[4]){
  digitalWrite(F0, ~A[0]);
  digitalWrite(F1, ~A[1]);
  digitalWrite(F2, ~A[2]);
  digitalWrite(F3, ~A[3]);
  i=0;
  int* v;
  while(i<4){
    v[i] = ~A[i];
    i++;
  }

  return v;
}

int* portaOr(int* A, int* B){
  /*digitalWrite(F0, (A[0]|B[0]));
  digitalWrite(F1, (A[1]|B[1]));
  digitalWrite(F2, (A[2]|B[2]));
  digitalWrite(F3, (A[3]|B[3]));*/
  i=0;
  int* v;
  while(i<4){
    v[i] = (A[i]|B[i]);
    i++;
  }
  Serial.println("v");
  Serial.print(v[0]);
  Serial.print(v[1]);
  Serial.print(v[2]);
  Serial.print(v[3]);
  
  return v;
}

int* portaAnd(int* A, int* B){
  /*digitalWrite(F0, (A[0]&B[0]));
  digitalWrite(F1, (A[1]&B[1]));
  digitalWrite(F2, (A[2]&B[2]));
  digitalWrite(F3, (A[3]&B[3]));*/
  i=0;
  int* v;
  while(i<4){
    v[i] = (A[i]&B[i]);
    i++;
  }

  return v;
}

int* portaXor(int* A, int* B){
  /*digitalWrite(F0, (A[0]^B[0]));
  digitalWrite(F1, (A[1]^B[1]));
  digitalWrite(F2, (A[2]^B[2]));
  digitalWrite(F3, (A[3]^B[3]));*/
  i=0;
  int* v;
  while(i<4){
    v[i] = (A[i]^B[i]);
    i++;
  }

  return v;
}

int* negado(int* v){
  i=0;
  while(i<4){
    v[i] = ~v[i];
    i++;
  }

  return v;
}

void acende(int* v){
  digitalWrite(F0, v[0]);
  digitalWrite(F1, v[1]);
  digitalWrite(F2, v[2]);
  digitalWrite(F3, v[3]);
}

void compile (){
  int* v;
  Serial.println("COMPILE"); //tirar
  delay(100);
  if(newCode[2] == 0){
    //zero logico
    zeroLogico();
  }
  else if(newCode[2] == 1){
    //um logico
    umLogico();
  }
  else if(newCode[2] == 2){
    //a negado
    v = portaNot(a);
  }
  else if(newCode[2] == 3){
    //b negado
    v = portaNot(a);
  }
  else if(newCode[2] == 4){
    //a ou b
    v = portaOr(a,b);
  }
  else if(newCode[2] == 5){
    //a e b
    v = portaAnd(a,b);
  }
  else if(newCode[2] == 6){
    //a ou exclusivo b
    v = portaXor(a,b);
  }
  else if(newCode[2] == 7){
    //a e b negado
    v = portaAnd(a,negado(b));
  }
  else if(newCode[2] == 8){
    //a ou b negado
    v = portaOr(a,negado(b));
  }
  else if(newCode[2] == 9){
    //a ou exclusivo b negado
    v = portaXor(a,negado(b));
  }
  else if(newCode[2] == 10){
    //a negado ou b
    v = portaOr(negado(a),b);
  }
  else if(newCode[2] == 11){
    //a ou b negado
    v = portaOr(a,negado(b));
  }
  else if(newCode[2] == 12){
    //a negado e b
    v = portaAnd(negado(a),b);
  }
  else if(newCode[2] == 13){
    //a e b negado
    v = portaAnd(a,negado(b));
  }
  else if(newCode[2] == 14){
    //a negado ou b negado
    v = portaOr(negado(a),negado(b));
  }
  else if(newCode[2] == 15){
    //a negado e b negado
    Serial.println("A NEGADO B NEGADO");
    v = portaAnd(negado(a),negado(b));
  }

  acende(v);
}


/**
 * Função que recebe uma String bytes e tranforma ele em um vetor de inteiro
 */
void convert(String str){
  while(i<3){
    if((int)str[i] >= 48 && (int)str[i] <= 57){
      //é um número do hex
      delay(100);
      j = (int)str[i] - 48;
      newCode[i] = j;
    }
    else if(((int)str[i] >= 65 && (int)str[i] <= 70) || ((int)str[i] >= 97 && (int)str[i] <= 122)){
      //é uma letra do hex
      delay(100);
      Serial.print("ERRO");
      switch(str[i]){
        case 'A': j = 10; newCode[i] = j; break;
        case 'a': j = 10; newCode[i] = j; break;
        case 'B': j = 11; newCode[i] = j; break;
        case 'b': j = 11; newCode[i] = j; break;
        case 'C': j = 12; newCode[i] = j; break;
        case 'c': j = 12; newCode[i] = j; break;
        case 'D': j = 13; newCode[i] = j; break;
        case 'd': j = 13; newCode[i] = j; break;
        case 'E': j = 14; newCode[i] = j; break;
        case 'e': j = 14; newCode[i] = j; break;
        case 'F': j = 15; newCode[i] = j; break;
        case 'f': j = 15; newCode[i] = j; break;
        
      }
    }
    i+=1;
  }
  toBin();
  i=0;
   
}

void toBin(){
  Serial.println("TO BIN"); //tirar
  j=0;
  
  int aux1 = 0;
  int aux2 = 0;
  int aux3 = 0;
  
  while(j<4){
    a[3-j] = bitRead(newCode[0],j);
    b[3-j] = bitRead(newCode[1],j);
    s[3-j] = bitRead(newCode[2],j);
    
    j++;
  } 
  
  /*Serial.print("A: ");
  j=0;
  while(j<4){
    Serial.print(a[j]);
    j++;
  }
  Serial.print("B: ");
   j=0;
  while(j<4){
    Serial.print(b[j]);
    j++;
  }
  Serial.print("S: ");
   j=0;
  while(j<4){
    Serial.print(s[j]);
    j++;
  }*/ //tirar
}



void setup() {
  // put your setup code here, to run once:
  pinMode(F3, OUTPUT);
  pinMode(F2, OUTPUT);
  pinMode(F1, OUTPUT);
  pinMode(F0, OUTPUT);

  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeedly:
  if(Serial.available() > 0){
    str = Serial.readString();
    next = Serial.read();
    
    
    if(next != '\n'){
      Serial.println((int)str[0]);
      Serial.println((int)str[1]);
      Serial.println((int)str[2]);
      delay(100);
      
      convert(str);
      
      Serial.println("New Code"); //tirar 
      while(i<3){
        delay(100);
        Serial.print(newCode[i]);
        i+=1;
      }
      /*Serial.println("BitRead");
      Serial.print(bitRead(newCode[2],3));
      Serial.print(bitRead(newCode[2],2));
      Serial.print(bitRead(newCode[2],1));
      Serial.print(bitRead(newCode[2],0));*/
      compile();
      i=0; //tirar 
    }
  }
}

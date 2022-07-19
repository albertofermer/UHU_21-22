#include "CGArco.h"
#include <GL/glew.h>
#include <math.h>
#include "CGFigure.h"


CGArco::CGArco(GLfloat radio_interno, GLfloat radio_externo, GLfloat altura)
{
	GLint m = 25;
	numFaces = 2 * (4 * (m - 1)) + 16;
	numVertices = 2 * (4 * (m)) + 32;
	int verticesIndex = 0;
	int indexesIndex = 0;
	//Postes
	GLfloat anchura = radio_externo - radio_interno;

	GLfloat p_vertices[32][3] =
	{
		{+radio_interno,0,+anchura / 2}, //0
		{+radio_externo,0,+anchura / 2}, //1
		{+radio_externo,+altura,+anchura / 2}, //2
		{+radio_interno,+altura,+anchura / 2}, //3
		{+radio_externo,0,+anchura / 2}, //4
		{+radio_externo,0,-anchura / 2}, //5
		{+radio_externo,+altura,-anchura / 2}, //6
		{+radio_externo,+altura,+anchura / 2}, //7
		{+radio_externo,0,-anchura / 2},//8
		{+radio_interno,0,-anchura / 2},//9
		{+radio_interno,+altura,-anchura / 2},//10
		{+radio_externo,+altura,-anchura / 2},//11
		{+radio_interno,0,+anchura / 2},//12
		{+radio_interno,0,-anchura / 2},//13
		{+radio_interno,+altura,-anchura / 2},//14
		{+radio_interno,+altura,+anchura / 2},//15


		{-radio_externo,0,+anchura / 2},//16
		{-radio_interno,0,+anchura / 2},//17
		{-radio_interno,+altura,+anchura / 2},//18
		{-radio_externo,+altura,+anchura / 2},//19
		{-radio_interno,0,+anchura / 2},//20
		{-radio_interno,0,-anchura / 2},//21
		{-radio_interno,+altura,-anchura / 2},//22
		{-radio_interno,+altura,+anchura / 2},//23
		{-radio_interno,0,-anchura / 2},//24
		{-radio_externo,0,-anchura / 2},//25
		{-radio_externo,+altura,-anchura / 2},//26
		{-radio_interno,+altura,-anchura / 2},//27
		{-radio_externo,0,-anchura / 2},//28
		{-radio_externo,0,+anchura / 2},//29
		{-radio_externo,+altura,+anchura / 2},//30
		{-radio_externo,+altura,-anchura / 2},//31
	};

	// Normales

	GLfloat p_normals[32][3] =
	{
		{0.0f,0.0f,1.0f}, //0
		{0.0f,0.0f,1.0f}, //1
		{0.0f,0.0f,1.0f}, //2
		{0.0f,0.0f,1.0f}, //3
		{1.0f,0.0f,0.0f}, //4
		{1.0f,0.0f,0.0f}, //5
		{1.0f,0.0f,0.0f}, //6
		{1.0f,0.0f,0.0f}, //7
		{0.0f,0.0f,-1.0f},//8
		{0.0f,0.0f,-1.0f},//9
		{0.0f,0.0f,-1.0f},//10
		{0.0f,0.0f,-1.0f},//11
		{-1.0f,0.0f,0.0f},//12
		{-1.0f,0.0f,0.0f},//13
		{-1.0f,0.0f,0.0f},//14
		{-1.0f,0.0f,0.0f},//15


		{0.0f,0.0f,1.0f},//16
		{0.0f,0,1.0f},//17
		{0.0f,0.0f,1.0f},//18
		{0.0f,0.0f,1.0f},//19
		{1.0f,0.0f,0.0f},//20
		{1.0f,0.0f,0.0f},//21
		{1.0f,0.0f,0.0f},//22
		{1.0f,0.0f,0.0f},//23
		{0.0f,0.0f,-1.0f},//24
		{0.0f,0.0f,-1.0f},//25
		{0.0f,0.0f,-1.0f},//26
		{0.0f,0.0f,-1.0f},//27
		{-1.0f,0,0.0f},//28
		{-1.0f,0,0.0f},//29
		{-1.0f,0.0f,0.0f},//30
		{-1.0f,0.0f,0.0f},//31
	};


	normals = new GLfloat[numVertices * 3];

	for (int i = 0; i < 32; i++)
		for (int j = 0; j < 3; j++)
			normals[3 * i + j] = p_normals[i][j];


	verticesIndex += 32 * 3;

	// Índices

	GLushort p_indexes[16][3]
	{
		{0,1,2},
		{0,2,3},
		{4,5,6},
		{4,6,7},
		{8,9,10},
		{8,10,11},
		{12,14,13},
		{12,15,14},

		{16,17,18},
		{16,18,19},
		{20,21,22},
		{20,22,23},
		{24,25,26},
		{24,26,27},
		{28,29,30},
		{28,30,31},
	};



	indexesIndex += 16 * 3;
	vertices = new GLfloat[numVertices * 3];

	for (int i = 0; i < 32; i++)
		for (int j = 0; j < 3; j++)
		{
			vertices[3 * i + j] = p_vertices[i][j];
		}

	indexes = new GLushort[numFaces * 3];
	for (int i = 0; i < 16; i++)
	{
		for (int j = 0; j < 3; j++)
		{
			indexes[3 * i + j] = p_indexes[i][j];
		}
	}



	textures = new GLfloat[numVertices * 2];
	for (int i = 0; i < numVertices; i++)
		for (int j = 0; j < 2; j++)
			textures[2 * i + j] = 1.0f * j;


	//Cara frontal del arco

	int vIndex = 32;
	for (int j = 0; j < m; j++)
	{
		GLfloat mCos = (GLfloat)cos(glm::radians(180.0 * j / (m - 1)));
		GLfloat mSin = (GLfloat)sin(glm::radians(180.0 * j / (m - 1)));
		vertices[verticesIndex] = mCos * radio_interno;
		normals[verticesIndex] = 0.0f;
		vertices[verticesIndex + 1] = altura + (mSin * radio_interno);
		normals[verticesIndex + 1] = 0.0f;
		vertices[verticesIndex + 2] = anchura / 2;
		normals[verticesIndex + 2] = 1.0f;
		vertices[verticesIndex + 3] = mCos * radio_externo;
		normals[verticesIndex + 3] = 0.0f;
		vertices[verticesIndex + 4] = altura + (mSin * radio_externo);
		normals[verticesIndex + 4] = 0.0f;
		vertices[verticesIndex + 5] = anchura / 2;
		normals[verticesIndex + 5] = 1.0f;
		verticesIndex += 6;
	}



	for (int j = 0; j < (m - 1); j++)
	{
		indexes[indexesIndex] = vIndex;
		indexes[indexesIndex + 1] = vIndex + 1;
		indexes[indexesIndex + 2] = vIndex + 2;
		indexes[indexesIndex + 3] = vIndex + 1;
		indexes[indexesIndex + 4] = vIndex + 3;
		indexes[indexesIndex + 5] = vIndex + 2;
		indexesIndex += 6;
		vIndex += 2;
	}
	vIndex += 2;


	//Cara trasera del arco

	for (int j = 0; j < m; j++){
		GLfloat mCos = (GLfloat)cos(glm::radians(180.0 * j / (m - 1)));
		GLfloat mSin = (GLfloat)sin(glm::radians(180.0 * j / (m - 1)));
		vertices[verticesIndex] = mCos * radio_externo;
		normals[verticesIndex] = 0.0f;
		vertices[verticesIndex + 1] = altura + (mSin * radio_externo);
		normals[verticesIndex + 1] = 0.0f;
		vertices[verticesIndex + 2] = -anchura / 2;
		normals[verticesIndex + 2] = -1.0f;
		vertices[verticesIndex + 3] = mCos * radio_interno;
		normals[verticesIndex + 3] = 0.0f;
		vertices[verticesIndex + 4] = altura + (mSin * radio_interno);
		normals[verticesIndex + 4] = 0.0f;
		vertices[verticesIndex + 5] = -anchura / 2;
		normals[verticesIndex + 5] = -1.0f;
		verticesIndex += 6;
	}



	for (int j = 0; j < (m - 1); j++)	{
		indexes[indexesIndex] = vIndex;
		indexes[indexesIndex + 1] = vIndex + 1;
		indexes[indexesIndex + 2] = vIndex + 2;
		indexes[indexesIndex + 3] = vIndex + 1;
		indexes[indexesIndex + 4] = vIndex + 3;
		indexes[indexesIndex + 5] = vIndex + 2;
		indexesIndex += 6;
		vIndex += 2;
	}
	vIndex += 2;



	//Cara interior arco


	for (int j = 0; j < m; j++)	{
		GLfloat mCos = (GLfloat)cos(glm::radians(180.0 * j / (m - 1)));
		GLfloat mSin = (GLfloat)sin(glm::radians(180.0 * j / (m - 1)));
		vertices[verticesIndex] = mCos * radio_interno;
		normals[verticesIndex] = -mCos;
		vertices[verticesIndex + 1] = altura + (mSin * radio_interno);
		normals[verticesIndex + 1] = -mSin;
		vertices[verticesIndex + 2] = -anchura / 2;
		normals[verticesIndex + 2] = 0.0f;
		vertices[verticesIndex + 3] = mCos * radio_interno;
		normals[verticesIndex + 3] = -mCos;
		vertices[verticesIndex + 4] = altura + (mSin * radio_interno);
		normals[verticesIndex + 4] = -mSin;
		vertices[verticesIndex + 5] = anchura / 2;
		normals[verticesIndex + 5] = 0.0f;
		verticesIndex += 6;
	}



	for (int j = 0; j < (m - 1); j++)	{
		indexes[indexesIndex] = vIndex;
		indexes[indexesIndex + 1] = vIndex + 1;
		indexes[indexesIndex + 2] = vIndex + 2;
		indexes[indexesIndex + 3] = vIndex + 1;
		indexes[indexesIndex + 4] = vIndex + 3;
		indexes[indexesIndex + 5] = vIndex + 2;
		indexesIndex += 6;
		vIndex += 2;
	}
	vIndex += 2;


	//Cara exterior arco


	for (int j = 0; j < m; j++)	{
		GLfloat mCos = (GLfloat)cos(glm::radians(180.0 * j / (m - 1)));
		GLfloat mSin = (GLfloat)sin(glm::radians(180.0 * j / (m - 1)));
		vertices[verticesIndex] = mCos * radio_externo;
		normals[verticesIndex] = mCos;
		vertices[verticesIndex + 1] = altura + (mSin * radio_externo);
		normals[verticesIndex + 1] = mSin;
		vertices[verticesIndex + 2] = anchura / 2;
		normals[verticesIndex + 2] = 0.0f;
		vertices[verticesIndex + 3] = mCos * radio_externo;
		normals[verticesIndex + 3] = mCos;
		vertices[verticesIndex + 4] = altura + (mSin * radio_externo);
		normals[verticesIndex + 4] = mSin;
		vertices[verticesIndex + 5] = -anchura / 2;
		normals[verticesIndex + 5] = 0.0f;
		verticesIndex += 6;
	}



	for (int j = 0; j < (m - 1); j++)
	{
		indexes[indexesIndex] = vIndex;
		indexes[indexesIndex + 1] = vIndex + 1;
		indexes[indexesIndex + 2] = vIndex + 2;
		indexes[indexesIndex + 3] = vIndex + 1;
		indexes[indexesIndex + 4] = vIndex + 3;
		indexes[indexesIndex + 5] = vIndex + 2;
		indexesIndex += 6;
		vIndex += 2;
	}

	InitBuffers();

}
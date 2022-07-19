#include "CGCube.h"
#include <GL/glew.h>
#include "CGFigure.h"

///
/// FUNCION: CGCube::CGCube(GLfloat s)
///
/// PROPÓSITO: Construye un cubo de lado'2*s'
///
CGCube::CGCube(GLfloat s)
{
    numFaces = 12; // Number of faces
    numVertices = 24; // Number of vertices

    GLfloat p_normals[24][3] = {
      { 1.0f, 0.0f, 0.0f }, // Positive X // 0
      { 1.0f, 0.0f, 0.0f }, // Positive X // 1
      { 1.0f, 0.0f, 0.0f }, // Positive X // 2
      { 1.0f, 0.0f, 0.0f }, // Positive X // 3
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 4
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 5
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 6
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 7
      { -1.0f, 0.0f, 0.0f }, // Negative X // 8
      { -1.0f, 0.0f, 0.0f }, // Negative X // 9
      { -1.0f, 0.0f, 0.0f }, // Negative X // 10
      { -1.0f, 0.0f, 0.0f }, // Negative X // 11
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 12
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 13
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 14
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 15
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 16
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 17
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 18
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 19
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 20
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 21
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 22
      { 0.0f, 0.0f, -1.0f } // Negative Z // 23
    };
 
    GLfloat p_textures[24][2] = { // Array of texture coordinates
      { 0.0f, 0.0f }, // Positive X
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Positive Y
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative X
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative Y
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Positive Z
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative Z
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f }
    };

    GLfloat p_vertices[24][3] = {
      { +s, +s, +s }, // A0 // Positive X
      { +s, -s, +s }, // D0 
      { +s, -s, -s }, // D1 
      { +s, +s, -s }, // A1 

      { -s, +s, +s }, // B0 // Positive Y
      { +s, +s, +s }, // A0 
      { +s, +s, -s }, // A1 
      { -s, +s, -s }, // B1 

      { -s, -s, +s }, // C0 // Negative X
      { -s, +s, +s }, // B0 
      { -s, +s, -s }, // B1 
      { -s, -s, -s }, // C1

      { +s, -s, +s }, // D0 // Negative Y
      { -s, -s, +s }, // C0 
      { -s, -s, -s }, // C1
      { +s, -s, -s }, // D1 0

      { +s, +s, +s }, // A0 // Positive Z
      { -s, +s, +s }, // B0 
      { -s, -s, +s }, // C0 
      { +s, -s, +s }, // D0 0

      { +s, +s, -s }, // A1 // Negative Z
      { +s, -s, -s }, // D1 
      { -s, -s, -s }, // C1 
      { -s, +s, -s } // B1 
    };

    GLushort p_indexes[12][3] = { // Array of indexes
      { 0, 1, 2 },
      { 0, 2, 3 },
      { 4, 5, 6 },
      { 4, 6, 7 },
      { 8, 9, 10 },
      { 8, 10, 11 },
      { 12, 13, 14 },
      { 12, 14, 15 },
      { 16, 17, 18 },
      { 16, 18, 19 },
      { 20, 21, 22 },
      { 20, 22, 23 }
    };

    normals = new GLfloat[numVertices * 3];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 3; j++) normals[3 * i + j] = p_normals[i][j];
    vertices = new GLfloat[numVertices * 3];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 3; j++) vertices[3 * i + j] = p_vertices[i][j];
    textures = new GLfloat[numVertices * 2];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 2; j++) textures[2 * i + j] = p_textures[i][j];
    indexes = new GLushort[numFaces * 3];
    for (int i = 0; i < numFaces; i++)
        for (int j = 0; j < 3; j++) indexes[3 * i + j] = p_indexes[i][j];

    InitBuffers();
}
CGCube::CGCube(GLfloat s, GLfloat h, GLfloat r0)
{


    numFaces = 12 * 2; // Number of faces
    numVertices = 24 * 2; // Number of vertices

    GLfloat p_normals[48][3] = {
        // --- Bloque de la izquierda --- //
      { 1.0f, 0.0f, 0.0f }, // Positive X // 0
      { 1.0f, 0.0f, 0.0f }, // Positive X // 1
      { 1.0f, 0.0f, 0.0f }, // Positive X // 2
      { 1.0f, 0.0f, 0.0f }, // Positive X // 3
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 4
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 5
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 6
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 7
      { -1.0f, 0.0f, 0.0f }, // Negative X // 8
      { -1.0f, 0.0f, 0.0f }, // Negative X // 9
      { -1.0f, 0.0f, 0.0f }, // Negative X // 10
      { -1.0f, 0.0f, 0.0f }, // Negative X // 11
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 12
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 13
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 14
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 15
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 16
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 17
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 18
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 19
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 20
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 21
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 22
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 23

      // --- Bloque de la derecha --- //
      { 1.0f, 0.0f, 0.0f }, // Positive X // 0
      { 1.0f, 0.0f, 0.0f }, // Positive X // 1
      { 1.0f, 0.0f, 0.0f }, // Positive X // 2
      { 1.0f, 0.0f, 0.0f }, // Positive X // 3
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 4
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 5
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 6
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 7
      { -1.0f, 0.0f, 0.0f }, // Negative X // 8
      { -1.0f, 0.0f, 0.0f }, // Negative X // 9
      { -1.0f, 0.0f, 0.0f }, // Negative X // 10
      { -1.0f, 0.0f, 0.0f }, // Negative X // 11
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 12
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 13
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 14
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 15
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 16
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 17
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 18
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 19
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 20
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 21
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 22
      { 0.0f, 0.0f, -1.0f } // Negative Z // 23
    };

    GLfloat p_textures[48][2] = { // Array of texture coordinates
        // --- Bloque de la izquierda --- //
      { 0.0f, 0.0f }, // Positive X
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Positive Y
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative X
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative Y
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Positive Z
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative Z
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      // Bloque de la derecha//
      { 0.0f, 0.0f }, // Positive X
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Positive Y
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative X
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative Y
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Positive Z
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative Z
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f }
    };

    GLfloat p_vertices[48][3] = {
        // Bloque de la izquierda //
      { +s, h, s }, // A0 // Positive X
      { +s, 0, s }, // D0 
      { +s, 0, 0 }, // D1 
      { +s, h, 0 }, // A1 

      { 0, h, s }, // B0 // Positive Y
      { +s, h, s }, // A0 
      { +s, h, 0 }, // A1 
      { 0, h, 0 }, // B1 
      { 0, 0, s }, // C0 // Negative X
      { 0 , h, s }, // B0 
      { 0, h, 0}, // B1 
      { 0, 0, 0 }, // C1

      { +s, 0, s }, // D0 // Negative Y
      { 0, 0, s }, // C0 
      { 0, 0, 0 }, // C1
      { +s, 0, 0 }, // D1 0

      { +s, h, s }, // A0 // Positive Z
      { 0, h, s }, // B0 
      { 0, 0, s }, // C0 
      { +s, 0, s }, // D0 0

      { +s, h, 0 }, // A1 // Negative Z
      { +s, 0, 0 }, // D1 
      { 0, 0, 0 }, // C1 
      { 0, h, 0 }, // B1

      // ----------Bloque de la derecha-------------- //

      { +2 * s + r0 * 2, h, s }, // A0 // Positive X
      { +2 * s + r0 * 2, 0, s }, // D0 
      { +2 * s + r0 * 2, 0, 0 }, // D1 
      { +2 * s + r0 * 2, h, 0 }, // A1 

      { s + r0 * 2, h, s }, // B0 // Positive Y
      { +2 * s + r0 * 2, h, s }, // A0 
      { +2 * s + r0 * 2, h, 0 }, // A1 
      { s + r0 * 2, h, 0 }, // B1 

      { s + r0 * 2, 0, s }, // C0 // Negative X
      { s + r0 * 2, h, s }, // B0 
      { s + r0 * 2, h, 0}, // B1 
      { s + r0 * 2, 0, 0 }, // C1

      { +2 * s + r0 * 2, 0, s }, // D0 // Negative Y
      { s + r0 * 2, 0, s }, // C0 
      { s + r0 * 2, 0, 0 }, // C1
      { +2 * s + r0 * 2, 0, 0 }, // D1 0

      { +2 * s + r0 * 2, h, s }, // A0 // Positive Z
      { s + r0 * 2, h, s }, // B0 
      { s + r0 * 2,0, s }, // C0 
      { +2 * s + r0 * 2, 0, s }, // D0 0

      { +2 * s + r0 * 2, h, 0 }, // A1 // Negative Z
      { +2 * s + r0 * 2, 0, 0 }, // D1 
      { s + r0 * 2, 0, 0 }, // C1 
      { s + r0 * 2, h, 0 } // B1 
    };

    GLushort p_indexes[24][3] = { // Array of indexes
      { 0, 1, 2 },
      { 0, 2, 3 },
      { 4, 5, 6 },
      { 4, 6, 7 },
      { 8, 9, 10 },
      { 8, 10, 11 },
      { 12, 13, 14 },
      { 12, 14, 15 },
      { 16, 17, 18 },
      { 16, 18, 19 },
      { 20, 21, 22 },
      { 20, 22, 23 },

      { 24, 25, 26 },
      { 24, 26, 27 },
      { 28, 29, 30 },
      { 28, 30, 31 },
      { 32, 33, 34 },
      { 32, 34, 35 },
      { 36, 37, 38 },
      { 36, 38, 39 },
      { 40, 41, 42 },
      { 40, 42, 43 },
      { 44, 45, 46 },
      { 44, 46, 47 },
    };

    normals = new GLfloat[numVertices * 3];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 3; j++) normals[3 * i + j] = p_normals[i][j];
    vertices = new GLfloat[numVertices * 3];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 3; j++) vertices[3 * i + j] = p_vertices[i][j];
    textures = new GLfloat[numVertices * 2];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 2; j++) textures[2 * i + j] = p_textures[i][j];
    indexes = new GLushort[numFaces * 3];
    for (int i = 0; i < numFaces; i++)
        for (int j = 0; j < 3; j++) indexes[3 * i + j] = p_indexes[i][j];

    InitBuffers();
}

CGCube::CGCube(GLfloat s, GLfloat h, GLfloat r0, GLfloat r1)
{

    int p = 5, m = 20;
    numFaces = 12 * 2; // Number of faces
    numVertices = 24 * 2; // Number of vertices

    normals = new GLfloat[numVertices * 3];
    textures = new GLfloat[numVertices * 2];
    vertices = new GLfloat[numVertices * 3];
    indexes = new GLushort[numFaces * 3];

    int texturesIndex = 0;
    int normalsIndex = 0;
    int verticesIndex = 0;
    int indexesIndex = 0;

    GLfloat p_normals[48][3] = {
        // --- Bloque de la izquierda --- //
      { 1.0f, 0.0f, 0.0f }, // Positive X // 0
      { 1.0f, 0.0f, 0.0f }, // Positive X // 1
      { 1.0f, 0.0f, 0.0f }, // Positive X // 2
      { 1.0f, 0.0f, 0.0f }, // Positive X // 3
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 4
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 5
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 6
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 7
      { -1.0f, 0.0f, 0.0f }, // Negative X // 8
      { -1.0f, 0.0f, 0.0f }, // Negative X // 9
      { -1.0f, 0.0f, 0.0f }, // Negative X // 10
      { -1.0f, 0.0f, 0.0f }, // Negative X // 11
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 12
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 13
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 14
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 15
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 16
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 17
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 18
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 19
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 20
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 21
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 22
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 23

      // --- Bloque de la derecha --- //
      { 1.0f, 0.0f, 0.0f }, // Positive X // 0
      { 1.0f, 0.0f, 0.0f }, // Positive X // 1
      { 1.0f, 0.0f, 0.0f }, // Positive X // 2
      { 1.0f, 0.0f, 0.0f }, // Positive X // 3
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 4
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 5
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 6
      { 0.0f, 1.0f, 0.0f }, // Positive Y // 7
      { -1.0f, 0.0f, 0.0f }, // Negative X // 8
      { -1.0f, 0.0f, 0.0f }, // Negative X // 9
      { -1.0f, 0.0f, 0.0f }, // Negative X // 10
      { -1.0f, 0.0f, 0.0f }, // Negative X // 11
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 12
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 13
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 14
      { 0.0f, -1.0f, 0.0f }, // Negative Y // 15
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 16
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 17
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 18
      { 0.0f, 0.0f, 1.0f }, // Positive Z // 19
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 20
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 21
      { 0.0f, 0.0f, -1.0f }, // Negative Z // 22
      { 0.0f, 0.0f, -1.0f } // Negative Z // 23
    };

    GLfloat p_textures[48][2] = { // Array of texture coordinates
        // --- Bloque de la izquierda --- //
      { 0.0f, 0.0f }, // Positive X
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Positive Y
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative X
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative Y
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Positive Z
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative Z
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      // Bloque de la derecha//
      { 0.0f, 0.0f }, // Positive X
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Positive Y
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative X
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative Y
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Positive Z
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f },
      { 0.0f, 0.0f }, // Negative Z
      { 1.0f, 0.0f },
      { 0.0f, 1.0f },
      { 1.0f, 1.0f }
    };

    GLfloat p_vertices[48][3] = {
        // Bloque de la izquierda //
      { +s, h, s }, // A0 // Positive X
      { +s, 0, s }, // D0 
      { +s, 0, 0 }, // D1 
      { +s, h, 0 }, // A1 

      { 0, h, s }, // B0 // Positive Y
      { +s, h, s }, // A0 
      { +s, h, 0 }, // A1 
      { 0, h, 0 }, // B1 
      { 0, 0, s }, // C0 // Negative X
      { 0 , h, s }, // B0 
      { 0, h, 0}, // B1 
      { 0, 0, 0 }, // C1

      { +s, 0, s }, // D0 // Negative Y
      { 0, 0, s }, // C0 
      { 0, 0, 0 }, // C1
      { +s, 0, 0 }, // D1 0

      { +s, h, s }, // A0 // Positive Z
      { 0, h, s }, // B0 
      { 0, 0, s }, // C0 
      { +s, 0, s }, // D0 0

      { +s, h, 0 }, // A1 // Negative Z
      { +s, 0, 0 }, // D1 
      { 0, 0, 0 }, // C1 
      { 0, h, 0 }, // B1

      // ----------Bloque de la derecha-------------- //

      { +2 * s + r0 * 2, h, s }, // A0 // Positive X
      { +2 * s + r0 * 2, 0, s }, // D0 
      { +2 * s + r0 * 2, 0, 0 }, // D1 
      { +2 * s + r0 * 2, h, 0 }, // A1 

      { s + r0 * 2, h, s }, // B0 // Positive Y
      { +2 * s + r0 * 2, h, s }, // A0 
      { +2 * s + r0 * 2, h, 0 }, // A1 
      { s + r0 * 2, h, 0 }, // B1 

      { s + r0 * 2, 0, s }, // C0 // Negative X
      { s + r0 * 2, h, s }, // B0 
      { s + r0 * 2, h, 0}, // B1 
      { s + r0 * 2, 0, 0 }, // C1

      { +2 * s + r0 * 2, 0, s }, // D0 // Negative Y
      { s + r0 * 2, 0, s }, // C0 
      { s + r0 * 2, 0, 0 }, // C1
      { +2 * s + r0 * 2, 0, 0 }, // D1 0

      { +2 * s + r0 * 2, h, s }, // A0 // Positive Z
      { s + r0 * 2, h, s }, // B0 
      { s + r0 * 2,0, s }, // C0 
      { +2 * s + r0 * 2, 0, s }, // D0 0

      { +2 * s + r0 * 2, h, 0 }, // A1 // Negative Z
      { +2 * s + r0 * 2, 0, 0 }, // D1 
      { s + r0 * 2, 0, 0 }, // C1 
      { s + r0 * 2, h, 0 } // B1 
    };

    GLushort p_indexes[24][3] = { // Array of indexes
      { 0, 1, 2 },
      { 0, 2, 3 },
      { 4, 5, 6 },
      { 4, 6, 7 },
      { 8, 9, 10 },
      { 8, 10, 11 },
      { 12, 13, 14 },
      { 12, 14, 15 },
      { 16, 17, 18 },
      { 16, 18, 19 },
      { 20, 21, 22 },
      { 20, 22, 23 },

      { 24, 25, 26 },
      { 24, 26, 27 },
      { 28, 29, 30 },
      { 28, 30, 31 },
      { 32, 33, 34 },
      { 32, 34, 35 },
      { 36, 37, 38 },
      { 36, 38, 39 },
      { 40, 41, 42 },
      { 40, 42, 43 },
      { 44, 45, 46 },
      { 44, 46, 47 },
    };

    normals = new GLfloat[numVertices * 3];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 3; j++) normals[3 * i + j] = p_normals[i][j];
    vertices = new GLfloat[numVertices * 3];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 3; j++) vertices[3 * i + j] = p_vertices[i][j];
    textures = new GLfloat[numVertices * 2];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 2; j++) textures[2 * i + j] = p_textures[i][j];
    indexes = new GLushort[numFaces * 3];
    for (int i = 0; i < numFaces; i++)
        for (int j = 0; j < 3; j++) indexes[3 * i + j] = p_indexes[i][j];

    InitBuffers();
}

int CGCube::getId() {
    return identificador;
}

void CGCube::setId(int id) {
    identificador = id;
}
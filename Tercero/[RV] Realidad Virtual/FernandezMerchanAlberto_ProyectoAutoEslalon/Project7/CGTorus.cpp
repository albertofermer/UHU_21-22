#include "CGTorus.h"
#include <GL/glew.h>
#include <math.h>
#include "CGFigure.h"

//
// FUNCIÓN: CGTorus::CGTorus(GLint p, GLint m, GLfloat r0, GLfloat r1)
//
// PROPÓSITO: Crea un toro
//
// COMENTARIOS:
//
// 'p' es el número de capas en las que se divide el toro
// 'm' es el número de sectores en que se divide cada capa
// 'r0' es el radio interior del toro
// 'r1' es el radio exterior del toro
// 
CGTorus::CGTorus(GLint p, GLint m, GLfloat r0, GLfloat r1)
{
    numFaces = 2 * m * p; // Number of faces
    numVertices = (m + 1) * (p + 1); // Number of vertices
    normals = new GLfloat[numVertices * 3];
    textures = new GLfloat[numVertices * 2];
    vertices = new GLfloat[numVertices * 3];
    indexes = new GLushort[numFaces * 3];

    int texturesIndex = 0;
    int normalsIndex = 0;
    int verticesIndex = 0;
    int indexesIndex = 0;

    for (int i = 0; i <= m; i++)
    {
        for (int j = 0; j <= p; j++)
        {
            GLfloat pCos = (GLfloat)cos(glm::radians(360.0 * j / p));
            GLfloat pSin = (GLfloat)sin(glm::radians(360.0 * j / p));
             pCos = 1;
             pSin = 1;
            GLfloat mCos = (GLfloat)cos(glm::radians(360.0 * i / m));
            GLfloat mSin = (GLfloat)sin(glm::radians(360.0 * i / m));

            vertices[verticesIndex] = (r1 + r0 * pCos) * mCos;
            vertices[verticesIndex + 1] = (r1 + r0 * pCos) * mSin;
            vertices[verticesIndex + 2] = r0 * pSin;
            verticesIndex += 3;

            normals[normalsIndex] = pCos * mCos;
            normals[normalsIndex + 1] = pCos * mSin;
            normals[normalsIndex + 2] = pSin;
            normalsIndex += 3;

            textures[texturesIndex] = ((GLfloat)j) / p;
            textures[texturesIndex + 1] = ((GLfloat)i) / m;
            texturesIndex += 2;
        }
    }

    for (int i = 0; i < m; i++)
    {
        for (int j = 0; j < p; j++)
        {
            indexes[indexesIndex] = (p + 1) * i + j;
            indexes[indexesIndex + 1] = (p + 1) * (i + 1) + j;
            indexes[indexesIndex + 2] = (p + 1) * (i + 1) + j + 1;
            indexesIndex += 3;

            indexes[indexesIndex] = (p + 1) * i + j;
            indexes[indexesIndex + 1] = (p + 1) * (i + 1) + j + 1;
            indexes[indexesIndex + 2] = (p + 1) * i + j + 1;
            indexesIndex += 3;
        }
    }

    InitBuffers();
}
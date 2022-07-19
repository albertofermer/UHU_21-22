#include "CGFigure.h"
#include <GL/glew.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>

//
// FUNCI�N: CGFigure::~CGFigure()
//
// PROP�SITO: Destructor de la figura
//
CGFigure::~CGFigure()
{
	if (vertices != NULL) delete[] vertices;
	if (indexes != NULL) delete[] indexes;
	if (normals != NULL) delete[] normals;
	if (textures != NULL) delete[] textures;

	// Delete vertex buffer objects
	glDeleteBuffers(4, VBO);
	glDeleteVertexArrays(1, &VAO);
}



//
// FUNCI�N: CGFigure::InitBuffers()
//
// PROP�SITO: Crea el VAO y los VBO y almacena todos los datos
//            en la GPU.
//
void CGFigure::InitBuffers()
{
	// Create the Vertex Array Object
	glGenVertexArrays(1, &VAO);
	glBindVertexArray(VAO);

	// Create the Vertex Buffer Objects
	glGenBuffers(4, VBO);

	// Copy data to video memory
	// Vertex data
	int buffsize = sizeof(GLfloat) * numVertices * 3;
	glBindBuffer(GL_ARRAY_BUFFER, VBO[VERTEX_DATA]);
	glBufferData(GL_ARRAY_BUFFER, buffsize, vertices, GL_STATIC_DRAW);

	// Normal data
	glBindBuffer(GL_ARRAY_BUFFER, VBO[NORMAL_DATA]);
	glBufferData(GL_ARRAY_BUFFER, buffsize, normals, GL_STATIC_DRAW);

	// Texture coordinates
	buffsize = sizeof(GLfloat) * numVertices * 2;
	glBindBuffer(GL_ARRAY_BUFFER, VBO[TEXTURE_DATA]);
	glBufferData(GL_ARRAY_BUFFER, buffsize, textures, GL_STATIC_DRAW);

	// Indexes
	buffsize = sizeof(GLushort) * numFaces * 3;
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, VBO[INDEX_DATA]);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffsize, indexes, GL_STATIC_DRAW);

	delete[] vertices;
	delete[] indexes;
	delete[] normals;
	delete[] textures;

	vertices = NULL;
	indexes = NULL;
	normals = NULL;
	textures = NULL;

	glEnableVertexAttribArray(0); // Vertex position
	glBindBuffer(GL_ARRAY_BUFFER, VBO[VERTEX_DATA]);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);

	glEnableVertexAttribArray(1); // Vertex normals
	glBindBuffer(GL_ARRAY_BUFFER, VBO[NORMAL_DATA]);
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 0, 0);

	glEnableVertexAttribArray(2); // Vertex textures
	glBindBuffer(GL_ARRAY_BUFFER, VBO[TEXTURE_DATA]);
	glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 0, 0);
}

//
// FUNCI�N: CGFigure::SetMaterial(CGMaterial* m)
//
// PROP�SITO: Asigna el material de la figura
//
void CGFigure::SetMaterial(CGMaterial* mat)
{
	material = mat;
}

CGMaterial* CGFigure::GetMaterial()
{
	return material;
}

//
// FUNCI�N: CGFigure::ResetLocation()
//
// PROP�SITO: Asigna la posici�n inicial de la figura 
//
void CGFigure::ResetLocation()
{
	location = glm::mat4(1.0f);
}

//
// FUNCI�N: CGFigure::GetLocation()
// 
// PROP�SITO: Devuelve la matriz de posici�n de la figura.
//

glm::mat4 CGFigure::GetLocation()
{
	return location;
}

//
// FUNCI�N: CGFigure::Translate(glm::vec3 t)
//
// PROP�SITO: A�ade un desplazamiento a la matriz de posici�n de la figura 
//
void CGFigure::Translate(glm::vec3 t)
{
	location = glm::translate(location, t);
}

//
// FUNCI�N: CGFigure::Rotate(GLfloat angle, glm::vec3 axis)
//
// PROP�SITO: A�ade una rotaci�n a la matriz de posici�n de la figura 
//
void CGFigure::Rotate(GLfloat angle, glm::vec3 axis)
{
	location = glm::rotate(location, glm::radians(angle), axis);
}

//
// FUNCI�N: CGFigure::SetLocation(glm::mat4 loc)
//
// PROP�SITO: Asigna la posici�n del objecto 
//
void CGFigure::SetLocation(glm::mat4 loc)
{
	location = loc;
}


//
// FUNCI�N: CGFigure::Draw(CGShaderProgram * program, glm::mat4 projection, glm::mat4 view)
//
// PROP�SITO: Dibuja la figura
//
void CGFigure::Draw(CGShaderProgram* program, glm::mat4 projection, glm::mat4 view, glm::mat4 shadowViewMatrix)
{
	glm::mat4 mvp = projection * view * location;
	program->SetUniformMatrix4("MVP", mvp);
	program->SetUniformMatrix4("ViewMatrix", view);
	program->SetUniformMatrix4("ModelViewMatrix", view * location);
	program->SetUniformMatrix4("ShadowMatrix", shadowViewMatrix * location);
	material->SetUniforms(program);

	glBindVertexArray(VAO);
	glDrawElements(GL_TRIANGLES, numFaces * 3, GL_UNSIGNED_SHORT, NULL);
}

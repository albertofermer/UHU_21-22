#pragma once

#include <GL/glew.h>
#include <glm/glm.hpp>
#include "CGShaderProgram.h"
#include "CGPiece.h"

//
// CLASE: CGObject
//
// DESCRIPCIÓN: Clase abstracta que representa un objeto 
//              formado por varias piezas
// 
class CGObject {
protected:
	glm::mat4 model; // Model matrix

public:
	void ResetLocation();
	void Translate(glm::vec3 t);
	void Rotate(GLfloat angle, glm::vec3 axis);
	void SetPosition(glm::vec3 pos);
	void SetLocation(glm::mat4 loc); //Para asignarle un valor de la matriz de posicionamiento.
	glm::mat4 GetLocation();		// Para obtener el posicionamiento de la matriz. Se usa para detectar que el coche atraviese el arco.
	void Draw(CGShaderProgram* program, glm::mat4 projection, glm::mat4 view, glm::mat4 shadowViewMatrix);

	virtual int GetNumPieces() = 0;
	virtual CGPiece* GetPiece(int i) = 0;
};
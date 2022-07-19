#include "CGSlotCar.h"
#include "CGSlotCar_pieces.h"
#include <GL/glew.h>
#include "CGObject.h"
#include "CGPiece.h"

CGSlotCar::CGSlotCar()
{
	CGMaterial* mtl0 = new CGMaterial();
	mtl0->SetAmbientReflect(0.588f, 0.588f, 0.588f);
	mtl0->SetDifusseReflect(0.588f, 0.588f, 0.588f);
	mtl0->SetSpecularReflect(0.0f, 0.0f, 0.0f);
	mtl0->SetShininess(10.0f);
	mtl0->InitTexture("textures/SlotCarRed.jpg");

	CGMaterial* mtl1 = new CGMaterial();
	mtl1->SetAmbientReflect(0.588f, 0.588f, 0.588f);
	mtl1->SetDifusseReflect(0.588f, 0.588f, 0.588f);
	mtl1->SetSpecularReflect(0.0f, 0.0f, 0.0f);
	mtl1->SetShininess(10.0f);

	pieces[0] = new CGSlotCar_0(mtl0);
	pieces[1] = new CGSlotCar_1(mtl1);
}

CGSlotCar::~CGSlotCar()
{
	for (int i = 0; i < 2; i++) delete pieces[i];
}

int CGSlotCar::GetNumPieces()
{
	return 2;
}

CGPiece* CGSlotCar::GetPiece(int index)
{
	return pieces[index];
}


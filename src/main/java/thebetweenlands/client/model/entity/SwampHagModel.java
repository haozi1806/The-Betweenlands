package thebetweenlands.client.model.entity;

import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import thebetweenlands.client.model.MowzieModelBase;
import thebetweenlands.common.entity.monster.PeatMummy;
import thebetweenlands.common.entity.monster.SwampHag;

public class SwampHagModel<T extends SwampHag> extends MowzieModelBase<T> implements HeadedModel {

	private final ModelPart root;
	private final ModelPart body_base;
	private final ModelPart neck;
	private final ModelPart body_top;
	private final ModelPart armright;
	private final ModelPart legright1;
	private final ModelPart legleft1;
	private final ModelPart legright2;
	private final ModelPart legleft2;
	private final ModelPart head1;
	private final ModelPart head2;
	private final ModelPart jaw;
	
	private final ModelPart dat_detailed_hot_bod;
	private final ModelPart dat_detailed_hot_bod_2;
	private final ModelPart head;
    private final ModelPart dat_detailed_hot_bod_3;
    private final ModelPart cute_lil_butt;
    private final ModelPart spoopy_stinger;
    private final ModelPart beak_right;
    private final ModelPart beak_left;
    
    ModelPart[] partsWormWiggle;

	public SwampHagModel(ModelPart root) {
		this.root = root;
		this.body_base = root.getChild("body_base");
		this.body_top = this.body_base.getChild("body_top");
		this.armright = this.body_base.getChild("armright");
		this.neck = this.body_top.getChild("neck");
		this.head1 = this.neck.getChild("head1");
		this.head2 = this.head1.getChild("head2");
		this.jaw = this.head2.getChild("jaw");

		this.legleft1 = this.body_base.getChild("legleft1");
		this.legright1 = this.body_base.getChild("legright1");
		this.legleft2 = this.legleft1.getChild("legleft2");
		this.legright2 = this.legright1.getChild("legright2");

		dat_detailed_hot_bod = armright.getChild("dat_detailed_hot_bod");
		head = dat_detailed_hot_bod.getChild("head");
		dat_detailed_hot_bod_2 = dat_detailed_hot_bod.getChild("dat_detailed_hot_bod_2");
		dat_detailed_hot_bod_3 = dat_detailed_hot_bod_2.getChild("dat_detailed_hot_bod_3");
		cute_lil_butt = dat_detailed_hot_bod_3.getChild("cute_lil_butt");
		spoopy_stinger = cute_lil_butt.getChild("spoopy_stinger");
		beak_left = this.head.getChild("beak_left"); 
		beak_right = this.head.getChild("beak_right");

		partsWormWiggle = new ModelPart[] { 
				head,
				dat_detailed_hot_bod,
				dat_detailed_hot_bod_3,
				dat_detailed_hot_bod_2,
				cute_lil_butt,
				spoopy_stinger
				};

	        setInitPose();
	}

	public static LayerDefinition createModelLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition body = meshdefinition.getRoot();

		PartDefinition body_base = body.addOrReplaceChild("body_base", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -1.5F, -1.0F, 10, 10, 6), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		body_base.addOrReplaceChild("body_inner", CubeListBuilder.create().texOffs(42, 44).addBox(-4.0F, -1.5F, -0.5F, 8, 9, 5), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition body_top = body_base.addOrReplaceChild("body_top", CubeListBuilder.create().texOffs(0, 17).addBox(-6.0F, -8.0F, -2.0F, 12, 8, 8, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.18203784098300857F, 0.136659280431156F, -0.091106186954104F));

		body_base.addOrReplaceChild("vines2", CubeListBuilder.create().texOffs(69, 42).addBox(-2.5F, 0.0F, -3.9F, 5, 14, 7), PartPose.offsetAndRotation(3.0F, -2.0F, 2.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition toadstool1 = body_top.addOrReplaceChild("toadstool1", CubeListBuilder.create().texOffs(0, 34).addBox(-4.3F, -1.0F, -3.5F, 8, 2, 4), PartPose.offsetAndRotation(4.5F, -4.0F, 0.0F, 0.0F, -1.0471975511965976F, 0.0F));
		toadstool1.addOrReplaceChild("vines1", CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, 0.0F, -2.5F, 0, 10, 5), PartPose.offsetAndRotation(1.5F, 1.0F, -1.5F, 0.0F, 1.1383037381507017F, 0.0F));
		toadstool1.addOrReplaceChild("toadstool2", CubeListBuilder.create().texOffs(0, 41).addBox(-2.5F, 0.0F, -3.0F, 6, 1, 3), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, -0.22759093446006054F, 0.0F));
		toadstool1.addOrReplaceChild("toadstool3", CubeListBuilder.create().texOffs(0, 46).addBox(0.0F, 0.0F, -3.0F, 4, 1, 4), PartPose.offsetAndRotation(-3.0F, 2.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F));
		toadstool1.addOrReplaceChild("toadstool1b", CubeListBuilder.create().texOffs(25, 34).addBox(-3.3F, -1.0F, -4.5F, 6, 2, 1), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition mushroomstem = body_top.addOrReplaceChild("mushroomstem", CubeListBuilder.create().texOffs(11, 52).addBox(-0.5F, -2.0F, -0.5F, 1, 3, 1), PartPose.offsetAndRotation(5.4F, -9.0F, 3.3F, -0.045553093477052F, 0.136659280431156F, 0.31869712141416456F));
		mushroomstem.addOrReplaceChild("mushroomhat", CubeListBuilder.create().texOffs(11, 57).addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, 0.045553093477052F, -0.136659280431156F, -0.31869712141416456F));
		mushroomstem.addOrReplaceChild("mushroomhat2", CubeListBuilder.create().texOffs(16, 52).addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2), PartPose.offsetAndRotation(-0.8F, 1.2F, -2.5F, 0.045553093477052F, 0.136659280431156F, -0.31869712141416456F));

		PartDefinition armright = body_base.addOrReplaceChild("armright", CubeListBuilder.create().texOffs(42, 0).addBox(-0.5F, -1.0F, -1.5F, 2, 16, 3), PartPose.offsetAndRotation(-7.0F, -6.0F, 2.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition legleft = body_base.addOrReplaceChild("legleft1", CubeListBuilder.create().texOffs(42, 32).addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3), PartPose.offsetAndRotation(3.0F, 8.0F, 2.0F, -0.18203784098300857F, -0.18203784098300857F, 0.0F));
		PartDefinition legright = body_base.addOrReplaceChild("legright1", CubeListBuilder.create().texOffs(42, 20).addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3), PartPose.offsetAndRotation(-3.0F, 8.0F, 2.0F, -0.18203784098300857F, 0.18203784098300857F, 0.0F));
		legleft.addOrReplaceChild("legleft2", CubeListBuilder.create().texOffs(55, 32).addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.18203784098300857F, 0.0F, 0.0F));
		legright.addOrReplaceChild("legright2", CubeListBuilder.create().texOffs(55, 20).addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.18203784098300857F, 0.0F, 0.0F));

		PartDefinition neck = body_top.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(70, 0).addBox(-2.0F, -4.0F, -2.0F, 4, 5, 4), PartPose.offsetAndRotation(-0.7F, -7.4F, 0.0F, 0.8196066167365371F, 0.045553093477052F, -0.045553093477052F));
		PartDefinition head1 = neck.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(70, 10).addBox(-4.0F, -6.0F, -8.5F, 8, 6, 8), PartPose.offsetAndRotation(0.0F, -2.0F, 0.5F, -0.9560913642424937F, 0.045553093477052F, 0.045553093477052F));
		PartDefinition head2 = head1.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(70, 25).addBox(-3.5F, 0.0F, -3.5F, 7, 3, 3), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		head1.addOrReplaceChild("brain", CubeListBuilder.create().texOffs(90, 35).addBox(-3.5F, -5.5F, -8.0F, 7, 5, 7), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		head2.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(87, 0).addBox(-3.0F, -1.0F, -7.0F, 6, 2, 7), PartPose.offsetAndRotation(0.0F, 0.8F, -1.5F, 1.0016444577195458F, 0.0F, 0.0F));
		head1.addOrReplaceChild("toadstool4", CubeListBuilder.create().texOffs(20, 45).addBox(-2.7F, 0.0F, -0.9F, 4, 1, 3), PartPose.offsetAndRotation(4.0F, -4.0F, -0.4F, 0.0F, -2.231054382824351F, 0.0F));

		PartDefinition dat_detailed_hot_bod = armright.addOrReplaceChild("dat_detailed_hot_bod", CubeListBuilder.create().texOffs(116, 10).addBox(-1.5F, -1.5F, 0.F, 3, 3, 3), PartPose.offsetAndRotation(2F, 12.5F, -1.5F, 0F, 0F, 0F));
		PartDefinition head = dat_detailed_hot_bod.addOrReplaceChild("head", CubeListBuilder.create().texOffs(103, 10).addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3), PartPose.offsetAndRotation(0F, 0F, -1.5F, 0F, 0F, 0F));
		head.addOrReplaceChild("beak_left", CubeListBuilder.create().texOffs(103, 22).addBox(-2F, -2F, -2F, 2, 3, 3), PartPose.offsetAndRotation(1.5F, 0.5F, -1.5F, 0F, -0.31869712141416456F, 0F));
		head.addOrReplaceChild("beak_right", CubeListBuilder.create().texOffs(103, 17).addBox(0F, -1.5F, -2F, 2, 3, 3), PartPose.offsetAndRotation(-1.5F, 0F, -1.5F, 0F, 0.31869712141416456F, 0F));
		PartDefinition dat_detailed_hot_bod_2 = dat_detailed_hot_bod.addOrReplaceChild("dat_detailed_hot_bod_2", CubeListBuilder.create().texOffs(116, 10).addBox(-1.5F, -1.5F, 0F, 3, 3, 3), PartPose.offsetAndRotation(0F, 0F, 3F, 0F, 0F, 0F));
		PartDefinition dat_detailed_hot_bod_3 = dat_detailed_hot_bod_2.addOrReplaceChild("dat_detailed_hot_bod_3", CubeListBuilder.create().texOffs(116, 10).addBox(-1.5F, -1.5F, 0F, 3, 3, 3), PartPose.offsetAndRotation(0F, 0F, 3F, 0F, 0F, 0F));
		PartDefinition cute_lil_butt = dat_detailed_hot_bod_3.addOrReplaceChild("cute_lil_butt", CubeListBuilder.create().texOffs(116, 17).addBox(-1F, -1F, 0F, 2, 2, 2), PartPose.offsetAndRotation(0F, 0F, 3F, 0F, 0F, 0F));
		PartDefinition spoopy_stinger = cute_lil_butt.addOrReplaceChild("spoopy_stinger", CubeListBuilder.create().texOffs(116, 21).addBox(-0.5F, 0F, 0F, 1, 2, 2), PartPose.offsetAndRotation(0F, -1.3F, 2F, -0.18203784098300857F, 0F, 0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		
		this.dat_detailed_hot_bod.visible = false;
		limbSwingAmount = Math.min(limbSwingAmount, 0.25F);

		this.jaw.xRot = 1.0016444577195458F + entity.jawFloat;
		this.head2.xRot = -0.8196066167365371F;

		this.head1.yRot = netHeadYaw / (180F / Mth.PI) - 0.045553093477052F;
		this.head1.xRot = headPitch / (180F / Mth.PI) - 0.8196066167365371F;
		this.head1.zRot = headPitch / (180F / Mth.PI) + 0.045553093477052F;
  /*     if (entity.getTarget() != null) { // TODO make this work after some zzzzzzzzzzzz
            armright.xRot += -((float) Mth.PI / 2.5F);
        }
		else {
			armright.xRot = (float) (Math.cos(limbSwing * 0.6662F + (float)Mth.PI) * 2.0F * limbSwingAmount);
			armright.zRot = entity.breatheFloat* 0.5F;
		}

		legright1.xRot = (float) (Math.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount -0.18203784098300857F);
		legleft1.xRot = (float) (Math.cos(limbSwing * 0.6662F + (float)Mth.PI) * 1.4F * limbSwingAmount -0.18203784098300857F);
		legright1.yRot = 0.0F;
		legleft1.yRot = 0.0F;

		limbSwing = entity.tickCount;
        limbSwingAmount = 0.4F;
*/
		float globalSpeed = 1.7f;
		float globalDegree = 1.8f;
		float legDegree = 1.8f;
		float inverseFrequency = 4 / (globalSpeed);
		limbSwing = (float) ((-(inverseFrequency * Math.sin((2 * limbSwing - 1) / inverseFrequency) - 4 * limbSwing + Mth.PI) / 4) - 0.5 * Mth.PI);

		this.walk(this.body_base, globalSpeed, 0.1F * globalDegree, true, 0, 0.05F * globalDegree, limbSwing, limbSwingAmount);
		this.walk(this.legleft1, globalSpeed, 0.1F * globalDegree, false, 0, 0F, limbSwing, limbSwingAmount);
		this.walk(this.legright1, globalSpeed, 0.1F * globalDegree, false, 0, 0F, limbSwing, limbSwingAmount);
		this.walk(this.body_top, globalSpeed, 0.1F * globalDegree, true, -1f, 0.05F * globalDegree, limbSwing, limbSwingAmount);
		this.walk(this.neck, globalSpeed, 0.1F * globalDegree, true, -2f, 0.05F * globalDegree, limbSwing, limbSwingAmount);
		this.walk(this.head1, globalSpeed, 0.1F * globalDegree, true, -3f, -0.15F * globalDegree, limbSwing, limbSwingAmount);

		this.walk(this.legleft1, 0.5F * globalSpeed, 0.7F * legDegree, false, 0, 0F, limbSwing, limbSwingAmount);
		this.walk(this.legleft2, 0.5F * globalSpeed, 0.8F * legDegree, false, -2f, 0.6F * legDegree, limbSwing, limbSwingAmount);

		this.walk(this.legright1, 0.5F * globalSpeed, 0.7F * legDegree, true, 0, 0F, limbSwing, limbSwingAmount);
		this.walk(this.legright2, 0.5F * globalSpeed, 0.8F * legDegree, true, -2f, 0.6F * legDegree, limbSwing, limbSwingAmount);

		this.walk(this.armright, 1 * globalSpeed, 0.2f * globalDegree, true, -1f, -0.3f * globalDegree, limbSwing, limbSwingAmount);
		this.flap(this.root, 0.5f * globalSpeed, 0.2f * globalDegree, false, 3f, 0.4f, limbSwing, limbSwingAmount);
		this.flap(this.body_top, 0.5f * globalSpeed, 0.1f * globalDegree, false, 1f, 0.1f, limbSwing, limbSwingAmount);
		this.flap(this.armright, 0.5f * globalSpeed, 0.2f * globalDegree, true, 0.5f, 0.3f, limbSwing, limbSwingAmount);
		this.flap(this.neck, 0.5f * globalSpeed, 0.1f * globalDegree, false, 0.5f, 0.1f, limbSwing, limbSwingAmount);
		this.flap(this.head1, 0.5f * globalSpeed, 0.1f * globalDegree, false, 0f, 0.1f, limbSwing, limbSwingAmount);
		this.body_base.x -= (float) (Math.cos((limbSwing - 3) * 0.5 * globalSpeed) * limbSwingAmount);

		//TODO Fix this when peat mummies are added back
		if (entity.isRidingMummy()) {
			if (entity.isRidingMummy() && !((PeatMummy) entity.getMummyMount()).isSpawningFinished()) {

			} else {
				this.legright1.xRot = -1.4137167F;
				this.legright1.yRot = (Mth.PI / 10F);
				this.legright1.zRot = 0.07853982F;
				this.legleft1.xRot = -1.4137167F;
				this.legleft1.yRot = -(Mth.PI / 10F);
				this.legleft1.zRot = -0.07853982F;
			}

			if (entity.isRidingMummy() && ((PeatMummy) entity.getMummyMount()).isSpawningFinished()) {
				if (entity.getThrowTimer() < 90) {
					this.armright.xRot += -(Mth.PI / 5F) - this.convertDegtoRad(entity.getThrowTimer()) * 0.35F;
					this.armright.yRot = this.convertDegtoRad(entity.getThrowTimer());
				}

				if (entity.getThrowTimer() >= 10 && entity.getThrowTimer() <= 99) {
					this.dat_detailed_hot_bod.visible = true;
				}

				if (entity.getThrowTimer() >= 90) {
					this.armright.xRot -= (Mth.PI / 5F);
					this.armright.yRot = this.convertDegtoRad(90F) - this.convertDegtoRad(entity.getThrowTimer() - 90F) * 9F;
				}
			} else {
				this.armright.yRot = 0F;
				this.armright.xRot -= (Mth.PI / 5F);
			}
		}
	}

	public float convertDegtoRad(float angleIn) {
		return angleIn * (Mth.PI / 180F);
	}

	@Override
	public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
		setInitPose();
		float frame = entity.tickCount + partialTick;
		this.body_top.xScale = ((float) (1 + 0.08 * Math.sin(frame * 0.07)));
		this.body_top.yScale = ((float) (1 + 0.08 * Math.sin(frame * 0.07)));
		this.body_top.zScale = ((float) (1 + 0.08 * Math.sin(frame * 0.07)));

		this.neck.xScale = (1 / (float) (1 + 0.08 * Math.sin(frame * 0.07)));
		this.neck.yScale = (1 / (float) (1 + 0.08 * Math.sin(frame * 0.07)));
		this.neck.zScale = (1 / (float) (1 + 0.08 * Math.sin(frame * 0.07)));

		this.armright.xScale = (1 / (float) (1 + 0.08 * Math.sin(frame * 0.07)));
		this.armright.yScale = (1 / (float) (1 + 0.08 * Math.sin(frame * 0.07)));
		this.armright.zScale = (1 / (float) (1 + 0.08 * Math.sin(frame * 0.07)));

		this.walk(this.body_top, 0.07f, 0.05f, false, 1, 0, frame, 1);
		this.walk(this.neck, 0.07f, 0.05f, false, 0.5f, 0, frame, 1);
		this.walk(this.head1, 0.07f, 0.05f, false, 0f, 0, frame, 1);
		this.walk(this.armright, 0.07f, 0.1f, false, 0.5f, -0.1f, frame, 1);
		this.flap(this.armright, 0.07f, 0.1f, true, 0.5f, 0.15f, frame, 1);
		this.chainWave(this.partsWormWiggle, 0.3f, 0.2f, 2f, frame, 1);
		this.chainSwing(this.partsWormWiggle, 0.2f, 0.2f, 2f, frame, 1);
		this.swing(this.beak_right, 0.5f, 0.3f, false, 1, 0, frame, 1);
		this.swing(this.beak_left, 0.5f, 0.3f, true, 1, 0, frame, 1);
	}

	@Override
	public ModelPart getHead() {
		return this.head1;
	}
}

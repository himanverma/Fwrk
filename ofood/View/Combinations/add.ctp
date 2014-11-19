<div class="row">
    <div class="col-md-12">
<?php echo $this->Form->create('Combination'); ?>
    <div class="box box-primary">
        <div class="box-header">
            <h3>Add</h3>
        </div>
        <div class="box-body">
            <div class="form-group">
                <?php echo $this->Form->input('vendor_id',array(
                    'div' => FALSE,
                    'class' => 'form-control',
                    'placeholder' => ''
                ));
                ?>
            </div>
            <div class="form-group">
                <?php echo $this->Form->input('display_name',array(
                    'div' => FALSE,
                    'class' => 'form-control',
                    'placeholder' => ''
                ));
                ?>
            </div>
            <div class="form-group">
                <?php echo $this->Form->input('price',array(
                    'div' => FALSE,
                    'class' => 'form-control',
                    'placeholder' => ''
                ));
                ?>
            </div>
            <div class="form-group">
                <?php echo $this->Form->input('day',array(
                    'div' => FALSE,
                    'class' => 'form-control',
                    'placeholder' => ''
                ));
                ?>
            </div>
            <div class="form-group">
                <?php echo $this->Form->input('date',array(
                    'div' => FALSE,
                    'class' => 'form-control',
                    'placeholder' => ''
                ));
                ?>
            </div>
            <div class="form-group">
                <?php echo $this->Form->input('status',array(
                    'div' => FALSE,
                    'class' => 'form-control',
                    'placeholder' => ''
                ));
                ?>
            </div>
        </div>
        <div class="box-footer">
            <button class="btn btn-primary" type="submit">Add</button>
        </div>
        
        
    </div>
        <?php echo $this->Form->end(); ?>
    </div>
</div>



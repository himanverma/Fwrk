<div class="vendorDocuments form">
<?php echo $this->Form->create('VendorDocument'); ?>
	<fieldset>
		<legend><?php echo __('Edit Vendor Document'); ?></legend>
	<?php
		echo $this->Form->input('id');
		echo $this->Form->input('vendor_id');
		echo $this->Form->input('path');
		echo $this->Form->input('detail');
	?>
	</fieldset>
<?php echo $this->Form->end(__('Submit')); ?>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>

		<li><?php echo $this->Form->postLink(__('Delete'), array('action' => 'delete', $this->Form->value('VendorDocument.id')), array(), __('Are you sure you want to delete # %s?', $this->Form->value('VendorDocument.id'))); ?></li>
		<li><?php echo $this->Html->link(__('List Vendor Documents'), array('action' => 'index')); ?></li>
		<li><?php echo $this->Html->link(__('List Vendors'), array('controller' => 'vendors', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor'), array('controller' => 'vendors', 'action' => 'add')); ?> </li>
	</ul>
</div>

<div class="vendorDocuments view">
<h2><?php echo __('Vendor Document'); ?></h2>
	<dl>
		<dt><?php echo __('Id'); ?></dt>
		<dd>
			<?php echo h($vendorDocument['VendorDocument']['id']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Vendor'); ?></dt>
		<dd>
			<?php echo $this->Html->link($vendorDocument['Vendor']['name'], array('controller' => 'vendors', 'action' => 'view', $vendorDocument['Vendor']['id'])); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Path'); ?></dt>
		<dd>
			<?php echo h($vendorDocument['VendorDocument']['path']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Detail'); ?></dt>
		<dd>
			<?php echo h($vendorDocument['VendorDocument']['detail']); ?>
			&nbsp;
		</dd>
	</dl>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('Edit Vendor Document'), array('action' => 'edit', $vendorDocument['VendorDocument']['id'])); ?> </li>
		<li><?php echo $this->Form->postLink(__('Delete Vendor Document'), array('action' => 'delete', $vendorDocument['VendorDocument']['id']), array(), __('Are you sure you want to delete # %s?', $vendorDocument['VendorDocument']['id'])); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendor Documents'), array('action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Document'), array('action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendors'), array('controller' => 'vendors', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor'), array('controller' => 'vendors', 'action' => 'add')); ?> </li>
	</ul>
</div>
